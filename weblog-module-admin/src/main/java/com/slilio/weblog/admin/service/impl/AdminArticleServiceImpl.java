package com.slilio.weblog.admin.service.impl;

import com.google.common.collect.Lists;
import com.slilio.weblog.admin.model.vo.article.PublishArticleReqVO;
import com.slilio.weblog.admin.service.AdminArticleService;
import com.slilio.weblog.common.domain.dos.*;
import com.slilio.weblog.common.domain.mapper.*;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.exception.BizException;
import com.slilio.weblog.common.utils.Response;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

  /**
   * 发布文章
   *
   * @param publishArticleReqVO
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public Response publishArticle(PublishArticleReqVO publishArticleReqVO) {
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
        ArticleCategoryRelDO.builder().articleId(articleId).categoryId(categoryId).build();
    articleCategoryRelMapper.insert(articleCategoryRelDO);

    // 保存文章关联的标签集合
    List<String> publishTags = publishArticleReqVO.getTags();
    insertTags(articleId, publishTags);
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
}
