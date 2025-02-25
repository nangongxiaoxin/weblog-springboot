package com.slilio.weblog.admin.convert;

import com.slilio.weblog.admin.model.vo.comment.FindCommentPageListRspVO;
import com.slilio.weblog.common.domain.dos.CommentDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentConvert {
  /** 初始化convert实例 */
  CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);

  /**
   * 将DO转换为VO
   *
   * @param bean
   * @return
   */
  FindCommentPageListRspVO convertDO2VO(CommentDO bean);
}
