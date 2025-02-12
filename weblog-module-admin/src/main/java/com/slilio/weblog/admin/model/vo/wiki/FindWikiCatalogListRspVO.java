package com.slilio.weblog.admin.model.vo.wiki;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindWikiCatalogListRspVO {
  private Long id; // 目录ID
  private Long articleId;
  private String title;
  private Integer sort;
  private Integer level;
  private Boolean editing; // 是否处于编辑状态（用于前端是否显示编辑输入框）
  private List<FindWikiCatalogListRspVO> children;
}
