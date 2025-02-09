package com.slilio.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.slilio.weblog.admin.convert.ArticleDetailConvert;
import com.slilio.weblog.admin.event.DeleteArticleEvent;
import com.slilio.weblog.admin.event.PublishArticleEvent;
import com.slilio.weblog.admin.event.UpdateArticleEvent;
import com.slilio.weblog.admin.model.vo.article.*;
import com.slilio.weblog.admin.service.AdminArticleService;
import com.slilio.weblog.common.domain.dos.*;
import com.slilio.weblog.common.domain.mapper.*;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.exception.BizException;
import com.slilio.weblog.common.utils.PageResponse;
import com.slilio.weblog.common.utils.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class AdminArticleServiceImpl implements AdminArticleService {

  @Autowired private ArticleMapper articleMapper;
  @Autowired private ArticleContentMapper articleContentMapper;
  @Autowired private ArticleCategoryRelMapper articleCategoryRelMapper;
  @Autowired private CategoryMapper categoryMapper;
  @Autowired private TagMapper tagMapper;
  @Autowired private ArticleTagRelMapper articleTagRelMapper;
  @Autowired private ApplicationEventPublisher eventPublisher;
  @Autowired private TransactionTemplate transactionTemplate;

  /**
   * 发布文章
   *
   * @param publishArticleReqVO
   * @return
   */
  //  @Transactional(rollbackFor = Exception.class) //声明式注解无效
  @Override
  public Response publishArticle(PublishArticleReqVO publishArticleReqVO) {
    // 更换为编程试事务注解
    Long rfArticleId =
        transactionTemplate.execute(
            status -> {
              try {
                // vo转ArticleDO，并保存
                ArticleDO articleDO =
                    ArticleDO.builder()
                        .title(publishArticleReqVO.getTitle())
                        .cover(publishArticleReqVO.getCover())
                        .summary(publishArticleReqVO.getSummary())
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build();
                articleMapper.insert(articleDO);
                // 拿到上面插入记录的主键ID
                Long articleId = articleDO.getId();

                // vo转ArticleContentDO,并保存
                ArticleContentDO articleContentDO =
                    ArticleContentDO.builder()
                        .articleId(articleId)
                        .content(publishArticleReqVO.getContent())
                        .build();
                articleContentMapper.insert(articleContentDO);

                // 处理文章关联的分类
                Long categoryId = publishArticleReqVO.getCategoryId();

                // 校验分类是否真实存在
                CategoryDO categoryDO = categoryMapper.selectById(categoryId);
                if (Objects.isNull(categoryDO)) {
                  log.warn("==> 分类不存在，categoryId：{}", categoryId);
                  throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
                }

                ArticleCategoryRelDO articleCategoryRelDO =
                    ArticleCategoryRelDO.builder()
                        .articleId(articleId)
                        .categoryId(categoryId)
                        .build();
                articleCategoryRelMapper.insert(articleCategoryRelDO);

                // 保存文章关联的标签集合
                List<String> publishTags = publishArticleReqVO.getTags();
                insertTags(articleId, publishTags);

                return articleId;
              } catch (Exception e) {
                status.setRollbackOnly();
                log.error("发布文章失败，事务回滚：", e);
                return null;
              }
            });

    // 发送文章发布事件
    eventPublisher.publishEvent(new PublishArticleEvent(this, rfArticleId));

    return Response.success();
  }

  /**
   * 保存标签
   *
   * @param articleId
   * @param publishTags
   */
  private void insertTags(Long articleId, List<String> publishTags) {
    // 筛选已提交的标签（表中不存在的标签）
    List<String> notExistTags = null;
    // 筛选已提交的标签（表中存在的标签）
    List<String> existedTags = null;

    // 查出所有标签
    List<TagDO> tagDOS = tagMapper.selectList(null);

    // 如果表中还没有添加任何标签
    if (CollectionUtils.isEmpty(tagDOS)) {
      notExistTags = publishTags;
    } else {
      List<String> tagIds =
          tagDOS.stream().map(TagDO -> String.valueOf(TagDO.getId())).collect(Collectors.toList());
      // 表中已添加相关标签，则需要筛选
      // 通过标签的ID来筛选，包含对应的ID则表示提交的标签是表中存在的
      existedTags =
          publishTags.stream()
              .filter(publishTag -> tagIds.contains(publishTag))
              .collect(Collectors.toList());
      // 否则则是不存在的
      notExistTags =
          publishTags.stream()
              .filter(publishTag -> !tagIds.contains(publishTag))
              .collect(Collectors.toList());

      // 补充逻辑 按字符串提交的标签，可能因为字符大小写问题 eg：JAVA java
      Map<String, Long> tagNameIdMap =
          tagDOS.stream()
              .collect(Collectors.toMap(tagDO -> tagDO.getName().toLowerCase(), TagDO::getId));

      // 使用迭代器进行安全的删除操作
      Iterator<String> iterator = notExistTags.iterator();
      while (iterator.hasNext()) {
        String notExistTag = iterator.next();
        // 转小写，若map中含有相同的KEY，则表示该新标签为重复标签
        if (tagNameIdMap.containsKey(notExistTag.toLowerCase())) {
          // 从不存在的标签集合中清除
          iterator.remove();
          // 并将对应的ID添加到已存在的标签集合中
          existedTags.add(String.valueOf(tagNameIdMap.get(notExistTag.toLowerCase())));
        }
      }
    }

    // 将提交上来的，已存在与表中的标签，文章-标签关联关系入库
    if (!CollectionUtils.isEmpty(existedTags)) {
      List<ArticleTagRelDO> articleTagRelDOS = Lists.newArrayList();
      existedTags.forEach(
          tagId -> {
            ArticleTagRelDO articleTagRelDO =
                ArticleTagRelDO.builder().articleId(articleId).tagId(Long.valueOf(tagId)).build();
            articleTagRelDOS.add(articleTagRelDO);
          });
      // 批量插入
      articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);
    }

    // 将提交上来的，不存在于表中的标签，入库保存
    if (!CollectionUtils.isEmpty(notExistTags)) {
      // 首先先将标签入库，再获取对应的ID，再进行文章-标签关联关系入库
      List<ArticleTagRelDO> articleTagRelDOS = Lists.newArrayList();
      notExistTags.forEach(
          tagName -> {
            TagDO tagDO =
                TagDO.builder()
                    .name(tagName)
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            tagMapper.insert(tagDO);

            // 拿到保存的标签ID
            Long tagId = tagDO.getId();

            // 文章-标签关联关系
            ArticleTagRelDO articleTagRelDO =
                ArticleTagRelDO.builder().articleId(articleId).tagId(tagId).build();
            articleTagRelDOS.add(articleTagRelDO);
          });
      // 批量插入
      articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);
    }
  }

  /**
   * 删除文章
   *
   * @param deleteArticleReqVO
   * @return
   */
  @Override
  //  @Transactional(rollbackFor = Exception.class) //声明式注解无效
  public Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO) {
    // 更换为编程试事务注解
    Long rfArticleId =
        transactionTemplate.execute(
            status -> {
              try {
                Long articleId = deleteArticleReqVO.getId();

                // 删除文章
                articleMapper.deleteById(articleId);

                // 删除文章内容
                articleContentMapper.deleteByArticleId(articleId);

                // 删除文章-分类关联记录
                articleCategoryRelMapper.deleteByArticleId(articleId);

                // 删除文章-标签关联记录
                articleTagRelMapper.deleteByArticleId(articleId);

                return articleId;
              } catch (Exception e) {
                status.setRollbackOnly();
                log.error("删除失败，事务回滚：", e);
                return null;
              }
            });

    // 发布文章删除事件
    eventPublisher.publishEvent(new DeleteArticleEvent(this, rfArticleId));

    return Response.success();
  }

  /**
   * 查询文章分页数据
   *
   * @param findArticlePageListReqVO
   * @return
   */
  @Override
  public Response findArticlePageList(FindArticlePageListReqVO findArticlePageListReqVO) {
    // 获取当前页、以及每页需要展示的数据数量
    Long current = findArticlePageListReqVO.getCurrent();
    Long size = findArticlePageListReqVO.getSize();
    String title = findArticlePageListReqVO.getTitle();
    LocalDate startDate = findArticlePageListReqVO.getStartDate();
    LocalDate endDate = findArticlePageListReqVO.getEndDate();

    // 执行分页查询
    Page<ArticleDO> articleDOPage =
        articleMapper.selectPageList(current, size, title, startDate, endDate);

    List<ArticleDO> articleDOS = articleDOPage.getRecords();

    // DO转VO
    List<FindArticlePageListRspVO> vos = null;
    if (!CollectionUtils.isEmpty(articleDOS)) {
      vos =
          articleDOS.stream()
              .map(
                  articleDO ->
                      FindArticlePageListRspVO.builder()
                          .id(articleDO.getId())
                          .title(articleDO.getTitle())
                          .cover(articleDO.getCover())
                          .createTime(articleDO.getCreateTime())
                          .isTop(articleDO.getWeight() > 0) // 是否置顶
                          .build())
              .collect(Collectors.toList());
    }

    return PageResponse.success(articleDOPage, vos);
  }

  /**
   * 查询文章详情
   *
   * @param findArticleDetailReqVO
   * @return
   */
  @Override
  public Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO) {

    Long articleId = findArticleDetailReqVO.getId();
    // 查询文章
    ArticleDO articleDO = articleMapper.selectById(articleId);

    if (Objects.isNull(articleDO)) {
      log.warn("==> 查询的文章内容不存在，articleId：{}", articleId);
      throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
    }
    // 查询文章内容
    ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);
    // 查询文章分类
    ArticleCategoryRelDO articleCategoryRelDO =
        articleCategoryRelMapper.selectByArticleId(articleId);

    // 查询文章标签集合
    List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleId(articleId);
    // 对应标签ID集合
    List<Long> tagIds =
        articleTagRelDOS.stream().map(ArticleTagRelDO::getTagId).collect(Collectors.toList());

    // vo转do
    FindArticleDetailRspVO vo = ArticleDetailConvert.INSTANCE.convertDO2VO(articleDO);
    vo.setContent(articleContentDO.getContent());
    vo.setCategoryId(articleCategoryRelDO.getCategoryId());
    vo.setTagIds(tagIds);

    return Response.success(vo);
  }

  /**
   * 更新文章
   *
   * @param updateArticleReqVO
   * @return
   */
  //  @Transactional(rollbackFor = Exception.class) //声明式注解无效
  @Override
  public Response updateArticle(UpdateArticleReqVO updateArticleReqVO) {
    // 更换为编程试事务注解
    Long rfArticleId =
        transactionTemplate.execute(
            status -> {
              try {
                Long articleId = updateArticleReqVO.getId();

                // 1.更新文章标题
                // vo转ArticleDO,并更新
                ArticleDO articleDO =
                    ArticleDO.builder()
                        .id(articleId)
                        .title(updateArticleReqVO.getTitle())
                        .cover(updateArticleReqVO.getCover())
                        .summary(updateArticleReqVO.getSummary())
                        .updateTime(LocalDateTime.now())
                        .build();
                // 执行更新
                int count = articleMapper.updateById(articleDO);

                // 根据更新是否成功来判断是否成功
                if (count == 0) {
                  log.warn("==> 该文章不存在, articleId: {}", articleId);
                  throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
                }

                // 2.更新文章内容
                // vo转ArticleContent,并更新
                ArticleContentDO articleContentDO =
                    ArticleContentDO.builder()
                        .articleId(articleId)
                        .content(updateArticleReqVO.getContent())
                        .build();
                // 执行更新
                articleContentMapper.updateByArticleId(articleContentDO);

                // 3.更新文章分类
                Long categoryId = updateArticleReqVO.getCategoryId();
                // 校验分类是否存在
                CategoryDO categoryDO = categoryMapper.selectById(categoryId);
                if (Objects.isNull(categoryDO)) {
                  log.warn("==> 分类不存在, categoryId: {}", categoryId);
                  throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
                }

                // 先删除该文章关联的分类记录，在插入新的关联关系
                articleCategoryRelMapper.deleteByArticleId(articleId);
                ArticleCategoryRelDO articleCategoryRelDO =
                    ArticleCategoryRelDO.builder()
                        .articleId(articleId)
                        .categoryId(categoryId)
                        .build();
                // 执行插入新的关联关系
                articleCategoryRelMapper.insert(articleCategoryRelDO);

                // 4.更新文章关联的标签集合
                // 先删除文章对应的标签
                articleTagRelMapper.deleteByArticleId(articleId);
                List<String> publishTags = updateArticleReqVO.getTags();
                insertTags(articleId, publishTags);
                return articleId;
              } catch (Exception e) {
                status.setRollbackOnly();
                log.error("更新失败，事务回滚：", e);
                return null;
              }
            });

    // 发布文章修改事件
    eventPublisher.publishEvent(new UpdateArticleEvent(this, rfArticleId));

    return Response.success();
  }

  /**
   * 文章置顶
   *
   * @param updateArticleIsTopReqVO
   * @return
   */
  @Override
  public Response updateArticleIsTop(UpdateArticleIsTopReqVO updateArticleIsTopReqVO) {
    Long articleId = updateArticleIsTopReqVO.getId();
    Boolean isTop = updateArticleIsTopReqVO.getIsTop();

    // 默认权重为0
    Integer weight = 0;
    if (isTop) {
      // 查询出表中最大的权重值
      ArticleDO articleDO = articleMapper.selectMaxWeight();
      Integer maxWeight = articleDO.getWeight();
      // 最大权重加1
      weight = maxWeight + 1;
    }
    // 更新该篇文章的权重值
    articleMapper.updateById(ArticleDO.builder().id(articleId).weight(weight).build());

    return Response.success();
  }
}
