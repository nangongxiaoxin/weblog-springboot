package com.slilio.weblog.web.model.vo.comment;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCommentItemRspVO {

  private Long id; // 主键
  private String avatar; // 头像
  private String nickname; // 昵称
  private String website; // 网址
  private String content; // 评论内容
  private LocalDateTime createTime; // 发布时间
  private String replyNickname; // 回复用户昵称
  private List<FindCommentItemRspVO> childComments; // 子评论集合
  private Boolean isShowReplyForm; // 是否展开回复表单（默认false）
}
