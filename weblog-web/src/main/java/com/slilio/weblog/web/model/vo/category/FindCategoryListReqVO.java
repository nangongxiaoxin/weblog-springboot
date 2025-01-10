package com.slilio.weblog.web.model.vo.category;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCategoryListReqVO {
  /** 分类id */
  @NotNull(message = "分类ID不能为空")
  private Long id;
}
