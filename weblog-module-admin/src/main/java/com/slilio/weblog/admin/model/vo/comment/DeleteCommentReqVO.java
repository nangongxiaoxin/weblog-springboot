package com.slilio.weblog.admin.model.vo.comment;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "删除评论 VO")
public class DeleteCommentReqVO {
  @NotNull(message = "评论ID不能为空")
  private Long id;
}
