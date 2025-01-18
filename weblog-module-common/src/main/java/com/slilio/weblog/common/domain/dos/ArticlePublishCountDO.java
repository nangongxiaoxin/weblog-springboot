package com.slilio.weblog.common.domain.dos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticlePublishCountDO {
  private LocalDate date; // 日期
  private Long count; // 发布文章数量
}
