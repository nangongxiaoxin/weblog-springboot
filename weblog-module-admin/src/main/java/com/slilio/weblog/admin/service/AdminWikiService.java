package com.slilio.weblog.admin.service;

import com.slilio.weblog.admin.model.vo.wiki.AddWikiReqVO;
import com.slilio.weblog.common.utils.Response;

public interface AdminWikiService {
  /**
   * 新增知识库
   *
   * @param addWikiReqVO
   * @return
   */
  Response addWiki(AddWikiReqVO addWikiReqVO);
}
