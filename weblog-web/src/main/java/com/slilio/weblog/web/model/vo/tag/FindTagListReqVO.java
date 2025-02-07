package com.slilio.weblog.web.model.vo.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindTagListReqVO {
  private Long size; // 展示数量
}
