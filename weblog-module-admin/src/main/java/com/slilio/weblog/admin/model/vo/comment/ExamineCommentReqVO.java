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
@ApiModel(value = "评论审核 VO")
public class ExamineCommentReqVO {
  @NotNull(message = "评论ID不能为空")
  private Long id;

  @NotNull(message = "评论状态不能为空")
  private Integer status;

  private String reason; // 原因
}
