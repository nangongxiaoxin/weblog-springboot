package com.slilio.weblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentStatusEnum {
  // 异常代码
  WAIT_EXAMINE(1, "等待审核"),
  NORMAL(2, "正常"),
  EXAMINE_FAILED(3, "审核不通过"),
  ;

  private Integer code;
  private String description;
}
