package com.slilio.weblog.web.controller;

import com.slilio.weblog.common.aspect.ApiOperationLog;
import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.model.vo.comment.FindQQUserInfoReqVO;
import com.slilio.weblog.web.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论")
public class CommentController {
  @Autowired private CommentService commentService;

  @PostMapping("/qq/userInfo")
  @ApiOperation(value = "获取QQ用户信息")
  @ApiOperationLog(description = "获取QQ用户信息")
  public Response findQQUserInfo(@RequestBody @Validated FindQQUserInfoReqVO findQQUserInfoReqVO) {
    return commentService.findQQUserInfo(findQQUserInfoReqVO);
  }
}
