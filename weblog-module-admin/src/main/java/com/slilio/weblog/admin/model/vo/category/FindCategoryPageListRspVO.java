package com.slilio.weblog.admin.model.vo.category;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCategoryPageListRspVO {
  /** 分类 ID */
  private Long id;

  /** 分类名称 */
  private String name;

  /** 创建时间 */
  private LocalDateTime createTime;

  /** 文章总数 */
  private Integer articlesTotal;
}
