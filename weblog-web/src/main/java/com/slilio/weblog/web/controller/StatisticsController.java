package com.slilio.weblog.web.controller;

import com.slilio.weblog.common.aspect.ApiOperationLog;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@Api(tags = "统计信息")
public class StatisticsController {
  @Autowired private StatisticsService statisticsService;

  @PostMapping("/info")
  @ApiOperation(value = "前台统计信息")
  @ApiOperationLog(description = "前台统计信息")
  public Response findInfo() {
    return statisticsService.findInfo();
  }
}
