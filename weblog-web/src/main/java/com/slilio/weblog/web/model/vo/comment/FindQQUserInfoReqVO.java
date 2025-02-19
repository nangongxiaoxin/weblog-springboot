package com.slilio.weblog.web.model.vo.comment;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindQQUserInfoReqVO {

  @NotBlank(message = "QQ号不能为空")
  private String qq;
}
