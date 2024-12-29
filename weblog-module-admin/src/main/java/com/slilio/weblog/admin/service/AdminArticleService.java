package com.slilio.weblog.admin.service;

import com.slilio.weblog.admin.model.vo.article.PublishArticleReqVO;
import com.slilio.weblog.common.utils.Response;

public interface AdminArticleService {

  /**
   * 发布文章
   *
   * @param publishArticleReqVO
   * @return
   */
  Response publishArticle(PublishArticleReqVO publishArticleReqVO);
}
