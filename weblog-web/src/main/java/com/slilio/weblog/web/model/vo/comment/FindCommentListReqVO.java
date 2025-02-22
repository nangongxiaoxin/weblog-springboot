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
public class FindCommentListReqVO {
  @NotBlank(message = "路由地址不能为空")
  private String routerUrl;
}
