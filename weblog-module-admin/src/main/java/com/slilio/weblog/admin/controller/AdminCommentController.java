package com.slilio.weblog.admin.controller;

import com.slilio.weblog.admin.model.vo.comment.DeleteCommentReqVO;
import com.slilio.weblog.admin.model.vo.comment.FindCommentPageListReqVO;
import com.slilio.weblog.admin.service.AdminCommentService;
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
@RequestMapping("/admin/comment")
@Api(tags = "Admin 评论模块")
public class AdminCommentController {
  @Autowired private AdminCommentService adminCommentService;

  @PostMapping("/list")
  @ApiOperation(value = "查询评论分页数据")
  @ApiOperationLog(description = "查询评论分页数据")
  public Response findCommentPageList(
      @RequestBody @Validated FindCommentPageListReqVO findCommentPageListReqVO) {
    return adminCommentService.findCommentPageList(findCommentPageListReqVO);
  }

  @PostMapping("/delete")
  @ApiOperation(value = "评论删除")
  @ApiOperationLog(description = "评论删除")
  public Response deleteComment(@RequestBody @Validated DeleteCommentReqVO deleteCommentReqVO) {
    return adminCommentService.deleteComment(deleteCommentReqVO);
  }
}
