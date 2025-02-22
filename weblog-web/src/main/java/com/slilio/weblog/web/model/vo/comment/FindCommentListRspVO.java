package com.slilio.weblog.web.model.vo.comment;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCommentListRspVO {
  private Integer total; // 评论总数
  private List<FindCommentItemRspVO> comments; // 评论集合
}
