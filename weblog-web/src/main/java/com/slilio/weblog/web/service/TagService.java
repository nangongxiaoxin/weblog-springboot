package com.slilio.weblog.web.service;

import com.slilio.weblog.common.utils.Response;

public interface TagService {
  /**
   * 获取标签列表
   *
   * @return
   */
  Response findTagList();
}
