package com.slilio.weblog.admin.service;

import com.slilio.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.slilio.weblog.common.utils.Response;

public interface AdminBlogSettingsService {
  /**
   * 更新博客信息
   *
   * @param updateBlogSettingsReqVO
   * @return
   */
  Response updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);

  /**
   * 查询博客设置详情
   *
   * @return
   */
  Response findDetail();
}
