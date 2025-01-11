package com.slilio.weblog.web.model.vo.category;

import com.slilio.weblog.common.model.BasePageQuery;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCategoryArticlePageListReqVO extends BasePageQuery {
  /** 分类id */
  @NotNull(message = "分类ID不能为空")
  private Long id;
}
