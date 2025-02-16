package com.slilio.weblog.web.model.vo.wiki;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindWikiCatalogListReqVO {
  @NotNull(message = "知识库ID不能为空")
  private Long id;
}
