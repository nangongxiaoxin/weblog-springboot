package com.slilio.weblog.admin.controller;

import com.slilio.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.slilio.weblog.admin.service.AdminBlogSettingsService;
import com.slilio.weblog.common.aspect.ApiOperationLog;
import com.slilio.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/blog/settings")
@Api(tags = "Admin 博客设置模块")
public class AdminBlogSettingsController {

  @Autowired private AdminBlogSettingsService adminBlogSettingsService;

  @PostMapping("/update")
  @ApiOperation(value = "博客基础信息修改")
  @ApiOperationLog(description = "博客基础信息修改")
  public Response updateBlogSettings(
      @RequestBody @Validated UpdateBlogSettingsReqVO updateBlogSettingsReqVO) {
    return adminBlogSettingsService.updateBlogSettings(updateBlogSettingsReqVO);
  }

  @PostMapping("/detail")
  @ApiOperation(value = "获取博客设置详情")
  @ApiOperationLog(description = "获取博客设置详情")
  public Response findDetail() {
    return adminBlogSettingsService.findDetail();
  }
}
