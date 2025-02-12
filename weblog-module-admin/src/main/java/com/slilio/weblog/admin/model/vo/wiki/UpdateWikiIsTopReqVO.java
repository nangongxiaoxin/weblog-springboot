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
@ApiModel(value = "更新知识库置顶状态 VO")
public class UpdateWikiIsTopReqVO {
  @NotNull(message = "知识库ID不能为空")
  private Long id;

  @NotNull(message = "知识库置顶状态不能为空")
  private Boolean isTop;
}
