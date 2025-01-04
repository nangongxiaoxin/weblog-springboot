package com.slilio.weblog.common.enums;

import com.slilio.weblog.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
  // 通用异常状态码
  SYSTEM_ERROR("10000", "产生通用错误"),
  // 业务异常状态码
  PRODUCT_NOT_FOUND("20000", "产品不存在（测试使用）"),
  // 参数错误
  PARAM_NOT_VALID("10001", "参数错误"),
  // 登录失败
  LOGIN_FAIL("20000", "登录失败"),
  // 用户名或者密码错误
  USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),
  // 页面未登录 强行访问提示 即无权限
  UNAUTHORIZED("20002", "无访问权限，请先登录"),
  // 受限角色提示 写受限
  FORBIDDEN("20004", "演示账号仅供支持查询操作"),
  // 文章分类
  CATEGORY_NAME_IS_EXISTED("20005", "该分类已存在，请勿重复添加！"),
  // 用户不存在
  USERNAME_NOT_FOUNT("20003", "该用户不存在"),
  // 请勿添加重复的标签
  TAG_CANT_DUPLICATE("20006", "请勿添加表中已经存在的标签"),
  // 文件上传异常
  FILE_UPLOAD_FAILED("20008", "文件上传失败！"),
  // 标签不存在
  TAG_NOT_EXISTED("20007", "该标签不存在"),
  // 提交的分类不存在
  CATEGORY_NOT_EXISTED("20009", "提交的分类不存在！"),
  // 文章不存在
  ARTICLE_NOT_FOUND("20010", "该文章不存在！"),
  // 该分类下包含文章，请先删除对应文章，才能删除！
  CATEGORY_CAN_NOT_DELETE("20011", "该分类下包含文章，请先删除对应文章，才能删除！"),
  // 该标签下包含文章，请先删除对应文章，才能删除！
  TAG_CAN_NOT_DELETE("20012", "该标签下包含文章，请先删除对应文章，才能删除！");

  // 异常码
  private String errorCode;
  // 错误信息
  private String errorMessage;
}
