package com.slilio.weblog.web.model.vo.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchArticlePageListRspVO {
  private Long id; // 文章ID
  private String cover; // 封面
  private String title; // 标题
  private String summary; // 摘要
  private String createDate; // 发布日期
}
