package com.slilio.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.slilio.weblog.admin.convert.CommentConvert;
import com.slilio.weblog.admin.model.vo.comment.DeleteCommentReqVO;
import com.slilio.weblog.admin.model.vo.comment.FindCommentPageListReqVO;
import com.slilio.weblog.admin.model.vo.comment.FindCommentPageListRspVO;
import com.slilio.weblog.admin.service.AdminCommentService;
import com.slilio.weblog.common.domain.dos.CommentDO;
import com.slilio.weblog.common.domain.mapper.CommentMapper;
import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.exception.BizException;
import com.slilio.weblog.common.utils.PageResponse;
import com.slilio.weblog.common.utils.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class AdminCommentServiceImpl implements AdminCommentService {
  @Autowired private CommentMapper commentMapper;

  /**
   * 查询评论分页数据
   *
   * @param findCommentPageListReqVO
   * @return
   */
  @Override
  public Response findCommentPageList(FindCommentPageListReqVO findCommentPageListReqVO) {
    // 获取当前页、以及每页需要展示的数据数量
    Long current = findCommentPageListReqVO.getCurrent();
    Long size = findCommentPageListReqVO.getSize();
    LocalDate startDate = findCommentPageListReqVO.getStartDate();
    LocalDate endDate = findCommentPageListReqVO.getEndDate();
    String routerUrl = findCommentPageListReqVO.getRouterUrl();
    Integer status = findCommentPageListReqVO.getStatus();

    // 执行分页查询
    Page<CommentDO> commentDOPage =
        commentMapper.selectPageList(current, size, routerUrl, startDate, endDate, status);

    List<CommentDO> commentDOS = commentDOPage.getRecords();

    // DO转VO
    List<FindCommentPageListRspVO> vos = null;
    if (!CollectionUtils.isEmpty(commentDOS)) {
      vos =
          commentDOS.stream()
              .map(commentDO -> CommentConvert.INSTANCE.convertDO2VO(commentDO))
              .collect(Collectors.toList());
    }

    return PageResponse.success(commentDOPage, vos);
  }

  /**
   * 删除评论
   *
   * @param deleteCommentReqVO
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Response deleteComment(DeleteCommentReqVO deleteCommentReqVO) {
    Long commentId = deleteCommentReqVO.getId();

    // 查询该评论是否为一级评论，还是二级评论
    CommentDO commentDO = commentMapper.selectById(commentId);

    // 判断评论是否存在
    if (Objects.isNull(commentDO)) {
      log.warn("该评论不存在,commentId:{}", commentId);
      throw new BizException(ResponseCodeEnum.COMMENT_NOT_FOUND);
    }

    // 删除 此 评论
    commentMapper.deleteById(commentId);

    Long replyCommentId = commentDO.getReplyCommentId();
    // 一级评论
    if (Objects.isNull(replyCommentId)) {
      // 删除该一级评论下的子评论
      commentMapper.deleteByParentCommentId(commentId);
    } else {
      // 仅删除此评论，以及此评论下的所有回复
      deleteAllChildComment(commentId);
    }

    return Response.success();
  }

  /**
   * 递归删除所有子评论
   *
   * @param commentId
   */
  private void deleteAllChildComment(Long commentId) {
    // 查询此评论的所有回复
    List<CommentDO> childCommentDOS = commentMapper.selectByReplyCommentId(commentId);
    if (CollectionUtils.isEmpty(childCommentDOS)) {
      return;
    }
    // 循环递归删除
    childCommentDOS.forEach(
        childCommentDO -> {
          Long childCommentId = childCommentDO.getId();
          commentMapper.deleteById(childCommentId);
          // 递归调用
          deleteAllChildComment(childCommentId);
        });
  }
}
