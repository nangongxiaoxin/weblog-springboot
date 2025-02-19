package com.slilio.weblog.web.service;

import com.slilio.weblog.common.utils.Response;
import com.slilio.weblog.web.model.vo.comment.FindQQUserInfoReqVO;

// 评论
public interface CommentService {
  /**
   * 根据QQ号获取用户信息
   *
   * @param findQQUserInfoReqVO
   * @return
   */
  Response findQQUserInfo(FindQQUserInfoReqVO findQQUserInfoReqVO);
}
