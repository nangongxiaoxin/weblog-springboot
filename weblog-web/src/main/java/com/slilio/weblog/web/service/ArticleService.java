package com.slilio.weblog.web.service;

import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.model.vo.article.FindIndexArticlePageListReqVO;

public interface ArticleService {
  /**
   * 获取首页文章分页数据
   *
   * @param findIndexArticlePageListReqVO
   * @return
   */
  Response findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO);
}
