package com.slilio.weblog.web.model.vo.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindCategoryListReqVO {
  private Long size; // 展示数量
}
