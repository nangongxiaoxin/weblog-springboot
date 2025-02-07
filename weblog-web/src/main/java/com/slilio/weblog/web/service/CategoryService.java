package com.slilio.weblog.web.service;

import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.model.vo.category.FindCategoryArticlePageListReqVO;
import com.slilio.weblog.web.model.vo.category.FindCategoryListReqVO;

public interface CategoryService {
  /**
   * 获取分类列表
   *
   * @return
   */
  Response findCategoryList(FindCategoryListReqVO findCategoryListReqVO);

  /**
   * 获取分类下文章分页数据
   *
   * @param findCategoryArticlePageListReqVO
   * @return
   */
  Response findCategoryArticlePageList(
      FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO);
}
