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
@ApiModel(value = "查询知识库目录数据入参 VO")
public class FindWikiCatalogListReqVO {
  /** 知识库ID */
  @NotNull(message = "知识库ID不能为空")
  private Long id;
}
