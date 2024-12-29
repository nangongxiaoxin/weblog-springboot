package com.slilio.weblog.admin.controller;

import com.slilio.weblog.admin.model.vo.article.PublishArticleReqVO;
import com.slilio.weblog.admin.service.AdminArticleService;
import com.slilio.weblog.common.aspect.ApiOperationLog;
import com.slilio.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/article")
@Api(tags = "Admin 文章模块")
public class AdminArticleController {

  @Autowired private AdminArticleService adminArticleService;

  @PostMapping("/publish")
  @ApiOperation(value = "文章发布")
  @ApiOperationLog(description = "文章发布")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Response publishArticle(@RequestBody @Validated PublishArticleReqVO publishArticleReqVO) {
    return adminArticleService.publishArticle(publishArticleReqVO);
  }
}
