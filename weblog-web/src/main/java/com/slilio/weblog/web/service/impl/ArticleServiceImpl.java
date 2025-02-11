package com.slilio.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.slilio.weblog.admin.event.ReadArticleEvent;
import com.slilio.weblog.common.domain.dos.*;
import com.slilio.weblog.common.domain.mapper.*;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.exception.BizException;
import com.slilio.weblog.common.utils.PageResponse;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.convert.ArticleConvert;
import com.slilio.weblog.web.markdown.MarkdownHelper;
import com.slilio.weblog.web.model.vo.article.*;
import com.slilio.weblog.web.model.vo.category.FindCategoryListRspVO;
import com.slilio.weblog.web.model.vo.tag.FindTagListRspVO;
import com.slilio.weblog.web.service.ArticleService;
import com.slilio.weblog.web.utils.MarkdownStatsUtil;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
  @Autowired private ArticleMapper articleMapper;
  @Autowired private CategoryMapper categoryMapper;
  @Autowired private ArticleCategoryRelMapper articleCategoryRelMapper;
  @Autowired private TagMapper tagMapper;
  @Autowired private ArticleTagRelMapper articleTagRelMapper;
  @Autowired private ArticleContentMapper articleContentMapper;
  //  发布文章阅读事件 发布者 生产者
  @Autowired private ApplicationEventPublisher eventPublisher;

  /**
   * 获取首页文章分页数据
   *
   * @param findIndexArticlePageListReqVO
   * @return
   */
  @Override
  public Response findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO) {
    Long current = findIndexArticlePageListReqVO.getCurrent();
    Long size = findIndexArticlePageListReqVO.getSize();

    // 1.分页查询文章主题记录
    Page<ArticleDO> articleDOPage = articleMapper.selectPageList(current, size, null, null, null);

    // 返回分页数据
    List<ArticleDO> articleDOS = articleDOPage.getRecords();

    List<FindIndexArticlePageListRspVO> vos = null;
    if (!CollectionUtils.isEmpty(articleDOS)) {
      // 文章DO转VO
      vos =
          articleDOS.stream()
              .map(
                  articleDO -> {
                    FindIndexArticlePageListRspVO vo =
                        ArticleConvert.INSTANCE.convertDO2VO(articleDO);
                    vo.setIsTop(articleDO.getWeight() > 0); // 是否置顶
                    return vo;
                  })
              .collect(Collectors.toList());
      // 拿到所有文章的ID集合
      List<Long> articleIds =
          articleDOS.stream().map(ArticleDO::getId).collect(Collectors.toList());

      // 2.设置文章所属分类
      // 查询所有分类
      List<CategoryDO> categoryDOS = categoryMapper.selectList(Wrappers.emptyWrapper());
      // 转map，方便后续根据分类id获取对应的分类名称
      Map<Long, String> categoryIdNameMap =
          categoryDOS.stream().collect(Collectors.toMap(CategoryDO::getId, CategoryDO::getName));

      // 根据文章ID批量查询所有关联记录
      List<ArticleCategoryRelDO> articleCategoryRelDOS =
          articleCategoryRelMapper.selectByArticleIds(articleIds);

      vos.forEach(
          vo -> {
            Long currArticleId = vo.getId();
            // 过滤当前文章对应的关联数据
            Optional<ArticleCategoryRelDO> optional =
                articleCategoryRelDOS.stream()
                    .filter(rel -> Objects.equals(rel.getArticleId(), currArticleId))
                    .findAny();

            // 若不为空
            if (optional.isPresent()) {
              ArticleCategoryRelDO articleCategoryRelDO = optional.get();
              Long categoryId = articleCategoryRelDO.getCategoryId();
              // 通过分类ID从map中拿到对应的分类名称
              String categoryName = categoryIdNameMap.get(categoryId);

              FindCategoryListRspVO findCategoryListRspVO =
                  FindCategoryListRspVO.builder().id(categoryId).name(categoryName).build();
              // 设置到当前vo类中
              vo.setCategory(findCategoryListRspVO);
            }
          });

      // 3.设置文章标签
      // 查询所有标签
      List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());
      // 转Map，方便后续根据ID拿到对应的标签名称
      Map<Long, String> mapIdNameMap =
          tagDOS.stream().collect(Collectors.toMap(TagDO::getId, TagDO::getName));

      // 拿到所有文章的标签关联记录
      List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleIds(articleIds);
      vos.forEach(
          vo -> {
            Long currArticleId = vo.getId();
            // 过滤出当前文章的 标签关联id 记录
            List<ArticleTagRelDO> articleTagRelDOList =
                articleTagRelDOS.stream()
                    .filter(rel -> Objects.equals(rel.getArticleId(), currArticleId))
                    .collect(Collectors.toList());
            List<FindTagListRspVO> findTagListRspVOS = Lists.newArrayList();
            // 将关联记录DO转VO，并设置对应的标签名称
            articleTagRelDOList.forEach(
                articleTagRelDO -> {
                  Long tagId = articleTagRelDO.getTagId();
                  String tagName = mapIdNameMap.get(tagId);

                  FindTagListRspVO findTagListRspVO =
                      FindTagListRspVO.builder().id(tagId).name(tagName).build();
                  findTagListRspVOS.add(findTagListRspVO);
                });
            // 设置转换后的标签数据
            vo.setTags(findTagListRspVOS);
          });
    }
    return PageResponse.success(articleDOPage, vos);
  }

  /**
   * 获取文章详情
   *
   * @param findArticleDetailReqVO
   * @return
   */
  @Override
  public Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO) {
    Long articleId = findArticleDetailReqVO.getArticleId();
    ArticleDO articleDO = articleMapper.selectById(articleId);

    // 判断文章是否存在
    if (Objects.isNull(articleDO)) {
      log.warn("==> 该文章不存在, articleId: {}", articleId);
      throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
    }

    // 查询正文
    ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);
    String content = articleContentDO.getContent();

    // 计算Markdown正文字数
    Integer totalWords = MarkdownStatsUtil.calculateWordsCount(content);
    // 计算阅读时间
    String readingTimes = MarkdownStatsUtil.calculateReadingTime(totalWords);

    // DO转VO
    FindArticleDetailRspVO vo =
        FindArticleDetailRspVO.builder()
            .title(articleDO.getTitle())
            .createTime(articleDO.getCreateTime())
            .content(MarkdownHelper.convertMarkdown2Html(articleContentDO.getContent()))
            .readNum(articleDO.getReadNum())
            .totalWords(totalWords)
            .readTime(readingTimes)
            .build();

    // 查询所属分类
    ArticleCategoryRelDO articleCategoryRelDO =
        articleCategoryRelMapper.selectByArticleId(articleId);
    CategoryDO categoryDO = categoryMapper.selectById(articleCategoryRelDO.getCategoryId());
    vo.setCategoryId(categoryDO.getId());
    vo.setCategoryName(categoryDO.getName());

    // 查询标签
    List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleId(articleId);
    List<Long> tagIds =
        articleTagRelDOS.stream().map(ArticleTagRelDO::getTagId).collect(Collectors.toList());
    List<TagDO> tagDOS = tagMapper.selectByIds(tagIds);

    // 标签DO转VO
    List<FindTagListRspVO> tagVOS =
        tagDOS.stream()
            .map(
                tagDO -> FindTagListRspVO.builder().id(tagDO.getId()).name(tagDO.getName()).build())
            .collect(Collectors.toList());
    vo.setTags(tagVOS);

    // 上一篇
    ArticleDO preArticleDO = articleMapper.selectPreArticle(articleId);
    if (Objects.nonNull(preArticleDO)) {
      FindPreNextArticleRspVO preArticleVO =
          FindPreNextArticleRspVO.builder()
              .articleId(preArticleDO.getId())
              .articleTitle(preArticleDO.getTitle())
              .build();
      vo.setPreArticle(preArticleVO);
    }

    // 下一篇
    ArticleDO nextArticleDO = articleMapper.selectNextArticle(articleId);
    if (Objects.nonNull(nextArticleDO)) {
      FindPreNextArticleRspVO nextArticleVO =
          FindPreNextArticleRspVO.builder()
              .articleId(nextArticleDO.getId())
              .articleTitle(nextArticleDO.getTitle())
              .build();
      vo.setNextArticle(nextArticleVO);
    }

    // 发布文章阅读事件
    eventPublisher.publishEvent(new ReadArticleEvent(this, articleId));

    return Response.success(vo);
  }
}
