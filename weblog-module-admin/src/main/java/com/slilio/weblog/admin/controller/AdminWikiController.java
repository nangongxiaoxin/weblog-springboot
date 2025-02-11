package com.slilio.weblog.admin.controller;

import com.slilio.weblog.admin.model.vo.wiki.AddWikiReqVO;
import com.slilio.weblog.admin.model.vo.wiki.DeleteWikiReqVO;
import com.slilio.weblog.admin.model.vo.wiki.FindWikiPageListReqVO;
import com.slilio.weblog.admin.service.AdminWikiService;
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
@RequestMapping("/admin/wiki")
@Api(tags = "Admin 知识库模块")
public class AdminWikiController {
  @Autowired private AdminWikiService wikiService;

  @PostMapping("/add")
  @ApiOperation(value = "新增知识库")
  @ApiOperationLog(description = "新增知识库")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Response addWiki(@RequestBody @Validated AddWikiReqVO addWikiReqVO) {
    return wikiService.addWiki(addWikiReqVO);
  }

  @PostMapping("/delete")
  @ApiOperation(value = "知识库删除")
  @ApiOperationLog(description = "知识库删除")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Response deleteWiki(@RequestBody @Validated DeleteWikiReqVO deleteWikiReqVO) {
    return wikiService.deleteWiki(deleteWikiReqVO);
  }

  @PostMapping("/list")
  @ApiOperation(value = "查询知识库分页数据")
  @ApiOperationLog(description = "查询知识库分页数据")
  public Response findWikiPageList(
      @RequestBody @Validated FindWikiPageListReqVO findWikiPageListReqVO) {
    return wikiService.findWikiPageList(findWikiPageListReqVO);
  }
}
