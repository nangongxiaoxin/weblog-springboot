package com.slilio.weblog.web.service;

import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.model.vo.search.SearchArticlePageListReqVO;

public interface SearchService {
  /**
   * 关键词分页搜索
   *
   * @param searchArticlePageListReqVO
   * @return
   */
  Response searchArticlePageList(SearchArticlePageListReqVO searchArticlePageListReqVO);
}
