package com.slilio.weblog.web.model.vo.wiki;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindWikiListRspVO {
  private Long id;
  private String cover;
  private String title;
  private String summary;

  private Boolean isTop; // 是否置顶
  private Long firstArticleId; // 第一遍文章ID
}
