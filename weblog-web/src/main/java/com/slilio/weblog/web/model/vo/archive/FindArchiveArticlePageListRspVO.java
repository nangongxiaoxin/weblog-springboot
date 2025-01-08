package com.slilio.weblog.web.model.vo.archive;

import java.time.YearMonth;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindArchiveArticlePageListRspVO {
  /** 归档的月份 */
  private YearMonth month;

  private List<FindArchiveArticleRspVO> articles;
}
