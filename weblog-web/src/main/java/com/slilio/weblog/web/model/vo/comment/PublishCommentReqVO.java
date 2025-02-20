package com.slilio.weblog.web.model.vo.comment;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublishCommentReqVO {
  private String avatar; // 头像

  @NotBlank(message = "昵称不能为空")
  private String nickname;

  @NotBlank(message = "邮箱不能为空")
  @Email(message = "邮箱格式有误")
  private String mail;

  private String website; // 网址

  @NotBlank(message = "路由地址不能为空")
  private String routerUrl;

  @NotBlank(message = "评论内容不能为空")
  @Length(min = 1, max = 120, message = "评论内容大于1小于120字符")
  private String content;

  private Long replyCommentId; // 回复的评论ID

  private Long parentCommentId; // 父评论ID
}
