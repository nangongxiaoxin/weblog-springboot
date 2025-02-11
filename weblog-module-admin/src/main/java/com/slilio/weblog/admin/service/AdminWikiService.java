package com.slilio.weblog.admin.service;

import com.slilio.weblog.admin.model.vo.wiki.AddWikiReqVO;
import com.slilio.weblog.admin.model.vo.wiki.DeleteWikiReqVO;
import com.slilio.weblog.admin.model.vo.wiki.FindWikiPageListReqVO;
import com.slilio.weblog.common.utils.Response;

public interface AdminWikiService {
  /**
   * 新增知识库
   *
   * @param addWikiReqVO
   * @return
   */
  Response addWiki(AddWikiReqVO addWikiReqVO);

  /**
   * 删除知识库
   *
   * @param deleteWikiReqVO
   * @return
   */
  Response deleteWiki(DeleteWikiReqVO deleteWikiReqVO);

  /**
   * 知识库分页查询
   *
   * @param findWikiPageListReqVO
   * @return
   */
  Response findWikiPageList(FindWikiPageListReqVO findWikiPageListReqVO);
}
