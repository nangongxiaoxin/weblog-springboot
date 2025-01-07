package com.slilio.weblog.web.controller;

import com.slilio.weblog.common.aspect.ApiOperationLog;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.service.BlogSettingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog/settings")
@Api(tags = "博客设置")
public class BlogSettingsController {
  @Autowired private BlogSettingsService blogSettingsService;

  /**
   * 获取博客信息
   *
   * @return
   */
  @PostMapping("/detail")
  @ApiOperation(value = "获取博客信息")
  @ApiOperationLog(description = "获取博客信息")
  public Response findDetail() {
    return blogSettingsService.findDetail();
  }
}
