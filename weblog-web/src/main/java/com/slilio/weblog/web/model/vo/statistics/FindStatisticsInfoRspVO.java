package com.slilio.weblog.web.model.vo.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindStatisticsInfoRspVO {
  private Long articleTotalCount; // 文章总数
  private Long categoryTotalCount; // 分类总数
  private Long tagTotalCount; // 标签总数
  private Long pvTotalCount; // 浏览总数
}
