package com.slilio.weblog.web.service.impl;

import com.slilio.weblog.common.domain.dos.BlogSettingsDO;
import com.slilio.weblog.common.domain.mapper.BlogSettingsMapper;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.convert.BlogSettingsConvert;
import com.slilio.weblog.web.model.vo.blogsettings.FindBlogSettingsDetailRspVO;
import com.slilio.weblog.web.service.BlogSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BlogSettingsServiceImpl implements BlogSettingsService {
  @Autowired private BlogSettingsMapper blogSettingsMapper;

  /**
   * 获取博客信息
   *
   * @return
   */
  @Override
  public Response findDetail() {
    // 获取博客信息（约定的ID为1）
    BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
    // DO转VO
    FindBlogSettingsDetailRspVO vo = BlogSettingsConvert.INSTANCE.convertDO2VO(blogSettingsDO);

    return Response.success(vo);
  }
}
