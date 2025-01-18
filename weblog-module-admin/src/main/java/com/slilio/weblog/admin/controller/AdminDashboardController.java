package com.slilio.weblog.admin.controller;

import com.slilio.weblog.admin.service.AdminDashboardService;
import com.slilio.weblog.common.aspect.ApiOperationLog;
import com.slilio.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
@Api(tags = "Admin 仪表盘")
public class AdminDashboardController {
  @Autowired private AdminDashboardService adminDashboardService;

  @PostMapping("/statistics")
  @ApiOperation(value = "获取仪表盘基础统计信息")
  @ApiOperationLog(description = "获取仪表盘基础统计信息")
  public Response findDashboardStatistics() {
    return adminDashboardService.findDashboardStatistics();
  }

  @PostMapping("/publishArticle/statistics")
  @ApiOperation(value = "获取后台仪表盘文章发布热点统计信息")
  @ApiOperationLog(description = "获取后台仪表盘文章发布热点统计信息")
  public Response findDashboardPublishArticleStatistics() {
    return adminDashboardService.findDashBoardPublishArticleStatistics();
  }
}
