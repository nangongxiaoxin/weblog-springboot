package com.slilio.weblog.web.service;

import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.model.vo.comment.FindCommentListReqVO;
import com.slilio.weblog.web.model.vo.comment.FindQQUserInfoReqVO;
import com.slilio.weblog.web.model.vo.comment.PublishCommentReqVO;

// 评论
public interface CommentService {
  /**
   * 根据QQ号获取用户信息
   *
   * @param findQQUserInfoReqVO
   * @return
   */
  Response findQQUserInfo(FindQQUserInfoReqVO findQQUserInfoReqVO);

  /**
   * 发布评论
   *
   * @param publishCommentReqVO
   * @return
   */
  Response publishComment(PublishCommentReqVO publishCommentReqVO);

  /**
   * 查询页面所有评论
   *
   * @param findCommentListReqVO
   * @return
   */
  Response findCommentList(FindCommentListReqVO findCommentListReqVO);
}
