package com.slilio.weblog.web.model.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindQQUserInfoRspVO {

  private String avatar; // 头像
  private String nickname; // 昵称
  private String mail; // 邮箱
}
