package com.slilio.weblog.admin.service;

import com.slilio.weblog.admin.model.vo.comment.DeleteCommentReqVO;
import com.slilio.weblog.admin.model.vo.comment.FindCommentPageListReqVO;
import com.slilio.weblog.common.utils.Response;

public interface AdminCommentService {
  /**
   * 查询评论分页数据
   *
   * @param findCommentPageListReqVO
   * @return
   */
  Response findCommentPageList(FindCommentPageListReqVO findCommentPageListReqVO);

  /**
   * 删除评论
   *
   * @param deleteArticleReqVO
   * @return
   */
  Response deleteComment(DeleteCommentReqVO deleteCommentReqVO);
}
