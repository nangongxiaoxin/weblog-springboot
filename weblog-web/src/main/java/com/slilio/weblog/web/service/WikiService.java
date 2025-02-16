package com.slilio.weblog.web.service;

import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.model.vo.wiki.FindWikiArticlePreNextReqVO;
import com.slilio.weblog.web.model.vo.wiki.FindWikiCatalogListReqVO;

public interface WikiService {
  /**
   * 获取知识库
   *
   * @return
   */
  Response findWikiList();

  /**
   * 获取知识库目录
   *
   * @param findWikiCatalogListReqVO
   * @return
   */
  Response findWikiCatalogList(FindWikiCatalogListReqVO findWikiCatalogListReqVO);

  /**
   * 获取上下页
   *
   * @param findWikiArticlePreNextReqVO
   * @return
   */
  Response findArticlePreNext(FindWikiArticlePreNextReqVO findWikiArticlePreNextReqVO);
}
