package com.slilio.weblog.common.enums;

import com.slilio.weblog.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    //通用异常状态码
    SYSTEM_ERROR("10000", "产生通用错误"),
    //业务异常状态码
    PRODUCT_NOT_FOUND("20000", "产品不存在（测试使用）"),
    //参数错误
    PARAM_NOT_VALID("10001", "参数错误"),
    //登录失败
    LOGIN_FAIL("20000", "登录失败"),
    //用户名或者密码错误
    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),
    //页面未登录 强行访问提示 即无权限
    UNAUTHORIZED("20002", "无访问权限，请先登录"),
    //受限角色提示 写受限
    FORBIDDEN("20004", "演示账号仅供支持查询操作"),
    //文章分类
    CATEGORY_NAME_IS_EXISTED("20005", "该分类已存在，请勿重复添加！"),
    //用户不存在
    USERNAME_NOT_FOUNT("20003", "该用户不存在");

    //异常码
    private String errorCode;
    //错误信息
    private String errorMessage;
}
