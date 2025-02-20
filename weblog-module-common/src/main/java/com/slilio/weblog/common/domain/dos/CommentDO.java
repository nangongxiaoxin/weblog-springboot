package com.slilio.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_comment")
public class CommentDO {
  @TableId(type = IdType.AUTO)
  private Long id;

  private String content;
  private String avatar;
  private String nickname;
  private String mail;
  private String website;
  private String routerUrl;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Boolean isDeleted;
  private Long replyCommentId;
  private Long parentCommentId;
  private Integer status;
  private String reason;
}
