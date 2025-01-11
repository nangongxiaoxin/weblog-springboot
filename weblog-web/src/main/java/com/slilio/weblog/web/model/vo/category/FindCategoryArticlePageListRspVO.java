package com.slilio.weblog.web.model.vo.category;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCategoryArticlePageListRspVO {
  private Long id;
  private String cover;
  private String title;

  /** 发布日期 */
  private LocalDate createDate;
}
