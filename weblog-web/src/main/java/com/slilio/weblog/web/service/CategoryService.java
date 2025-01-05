package com.slilio.weblog.web.service;

import com.slilio.weblog.common.utils.Response;

public interface CategoryService {
  /**
   * 获取分类列表
   *
   * @return
   */
  Response findCategoryList();
}
