package com.slilio.weblog.admin.controller;

import com.slilio.weblog.admin.model.vo.article.*;
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

  /**
   * 文章发布
   *
   * @param publishArticleReqVO
   * @return
   */
  @PostMapping("/publish")
  @ApiOperation(value = "文章发布")
  @ApiOperationLog(description = "文章发布")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Response publishArticle(@RequestBody @Validated PublishArticleReqVO publishArticleReqVO) {
    return adminArticleService.publishArticle(publishArticleReqVO);
  }

  /**
   * 删除文章
   *
   * @param deleteArticleReqVO
   * @return
   */
  @PostMapping("/delete")
  @ApiOperation(value = "删除文章")
  @ApiOperationLog(description = "删除文章")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Response deleteArticle(@RequestBody @Validated DeleteArticleReqVO deleteArticleReqVO) {
    return adminArticleService.deleteArticle(deleteArticleReqVO);
  }

  /**
   * 查询文章分页数据
   *
   * @param findArticlePageListReqVO
   * @return
   */
  @PostMapping("/list")
  @ApiOperation(value = "查询文章分页数据")
  @ApiOperationLog(description = "查询文章分页数据")
  public Response findArticlePageList(
      @RequestBody @Validated FindArticlePageListReqVO findArticlePageListReqVO) {
    return adminArticleService.findArticlePageList(findArticlePageListReqVO);
  }

  @PostMapping("/detail")
  @ApiOperation(value = "查询文章详情")
  @ApiOperationLog(description = "查询文章详情")
  public Response findArticleDetail(
      @RequestBody @Validated FindArticleDetailReqVO findArticleDetailReqVO) {
    return adminArticleService.findArticleDetail(findArticleDetailReqVO);
  }

  @PostMapping("/update")
  @ApiOperation(value = "更新文章")
  @ApiOperationLog(description = "更新文章")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Response updateArticle(@RequestBody @Validated UpdateArticleReqVO updateArticleReqVO) {
    return adminArticleService.updateArticle(updateArticleReqVO);
  }
}
