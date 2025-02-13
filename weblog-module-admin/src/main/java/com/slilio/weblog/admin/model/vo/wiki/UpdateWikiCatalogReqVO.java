package com.slilio.weblog.admin.model.vo.wiki;

import io.swagger.annotations.ApiModel;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "更新知识库目录数据入参VO")
public class UpdateWikiCatalogReqVO {
  @NotNull(message = "知识库ID不能为空")
  private Long id; // 知识库ID

  @Valid private List<UpdateWikiCatalogItemReqVO> catalogs; // 目录
}
