package com.slilio.weblog.admin.model.vo.wiki;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateWikiCatalogItemReqVO {
  @NotNull(message = "目录ID不能为空")
  private Long id; // 目录ID

  private Long articleId;

  @NotBlank(message = "目录标题不能为空")
  private String title;

  private Integer sort;

  private Integer level;

  private List<UpdateWikiCatalogItemReqVO> children;
}
