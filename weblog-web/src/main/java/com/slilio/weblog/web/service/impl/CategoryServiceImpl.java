package com.slilio.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slilio.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.slilio.weblog.common.domain.dos.ArticleDO;
import com.slilio.weblog.common.domain.dos.CategoryDO;
import com.slilio.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.slilio.weblog.common.domain.mapper.ArticleMapper;
import com.slilio.weblog.common.domain.mapper.CategoryMapper;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.exception.BizException;
import com.slilio.weblog.common.utils.PageResponse;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.convert.ArticleConvert;
import com.slilio.weblog.web.model.vo.category.FindCategoryArticlePageListReqVO;
import com.slilio.weblog.web.model.vo.category.FindCategoryArticlePageListRspVO;
import com.slilio.weblog.web.model.vo.category.FindCategoryListReqVO;
import com.slilio.weblog.web.model.vo.category.FindCategoryListRspVO;
import com.slilio.weblog.web.service.CategoryService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

  @Autowired private CategoryMapper categoryMapper;
  @Autowired private ArticleCategoryRelMapper articleCategoryRelMapper;
  @Autowired private ArticleMapper articleMapper;

  /**
   * 获取分类列表
   *
   * @return
   */
  @Override
  public Response findCategoryList(FindCategoryListReqVO findCategoryListReqVO) {
    Long size = findCategoryListReqVO.getSize();
    List<CategoryDO> categoryDOS = null;
    // 如果接口入参中未指定size
    if (Objects.isNull(size) || size == 0) {
      // 查明所有分类
      categoryDOS = categoryMapper.selectList(Wrappers.emptyWrapper());
    } else {
      // 否则查询指定数量
      categoryDOS = categoryMapper.selectByLimit(size);
    }

    // VO转DO
    List<FindCategoryListRspVO> vos = null;
    if (!CollectionUtils.isEmpty(categoryDOS)) {
      vos =
          categoryDOS.stream()
              .map(
                  categoryDO ->
                      FindCategoryListRspVO.builder()
                          .id(categoryDO.getId())
                          .name(categoryDO.getName())
                          .articleTotal(categoryDO.getArticlesTotal())
                          .build())
              .collect(Collectors.toList());
    }
    return Response.success(vos);
  }

  /**
   * 获取分类下文章分页数据
   *
   * @param findCategoryArticlePageListReqVO
   * @return
   */
  @Override
  public Response findCategoryArticlePageList(
      FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO) {
    Long current = findCategoryArticlePageListReqVO.getCurrent();
    Long size = findCategoryArticlePageListReqVO.getSize();
    Long categoryId = findCategoryArticlePageListReqVO.getId();

    CategoryDO categoryDO = categoryMapper.selectById(categoryId);

    // 判断该分类是否存在
    if (Objects.isNull(categoryDO)) {
      log.warn("==> 该分类不存在, categoryId: {}", categoryId);
      // 分类不存在
      throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
    }

    // 先查询该分类下所有关联的文章ID
    List<ArticleCategoryRelDO> articleCategoryRelDOS =
        articleCategoryRelMapper.selectListByCategoryId(categoryId);

    // 若该分类下没有任何文章
    if (CollectionUtils.isEmpty(articleCategoryRelDOS)) {
      log.info("==> 该分类下还未发布任何文章, categoryId: {}", categoryId);
      return PageResponse.success(null, null);
    }

    // 分类下文章list集合
    List<Long> articleIds =
        articleCategoryRelDOS.stream()
            .map(ArticleCategoryRelDO::getArticleId)
            .collect(Collectors.toList());

    // 根据文章id集合查询文章分页数据
    Page<ArticleDO> page = articleMapper.selectPageListByArticleIds(current, size, articleIds);
    List<ArticleDO> articleDOS = page.getRecords();

    // DO转VO
    List<FindCategoryArticlePageListRspVO> vos = null;
    if (!CollectionUtils.isEmpty(articleDOS)) {
      vos =
          articleDOS.stream()
              .map(articleDO -> ArticleConvert.INSTANCE.convertDO2CategoryArticleVO(articleDO))
              .collect(Collectors.toList());
    }

    return PageResponse.success(page, vos);
  }
}
