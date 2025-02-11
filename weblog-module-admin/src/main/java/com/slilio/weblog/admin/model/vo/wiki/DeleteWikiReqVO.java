package com.slilio.weblog.admin.model.vo.wiki;

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
@ApiModel(value = "删除知识库VO")
public class DeleteWikiReqVO {

  @NotNull(message = "知识库ID不能为空")
  private Long id;
}
