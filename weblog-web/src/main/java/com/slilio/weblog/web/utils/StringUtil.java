package com.slilio.weblog.web.utils;

public class StringUtil {

  /**
   * 判断字符串是否为纯数字
   *
   * @param str
   * @return
   */
  public static boolean isPureNumber(String str) {
    return str.matches("\\d+");
  }
}
