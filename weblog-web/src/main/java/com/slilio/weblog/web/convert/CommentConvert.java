package com.slilio.weblog.web.convert;

import com.slilio.weblog.common.domain.dos.CommentDO;
import com.slilio.weblog.web.model.vo.comment.FindCommentItemRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentConvert {
  /** 初始化convert实例 */
  CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);

  /**
   * CommentDO->FindCommentItemRspVO
   *
   * @param bean
   * @return
   */
  FindCommentItemRspVO convertDO2VO(CommentDO bean);
}
