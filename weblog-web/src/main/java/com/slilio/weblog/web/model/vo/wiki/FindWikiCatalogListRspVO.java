package com.slilio.weblog.web.model.vo.wiki;

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
  private Long id; // 知识库ID
  private Long articleId;
  private String title;
  private Integer level;

  private List<FindWikiCatalogListRspVO> children; // 二级目录
}
