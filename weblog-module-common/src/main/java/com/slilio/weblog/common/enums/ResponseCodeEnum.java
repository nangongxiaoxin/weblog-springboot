package com.slilio.weblog.common.enums;

import com.slilio.weblog.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    //通用异常状态码
    SYSTEM_ERROR("10000","产生通用错误"),
    //业务异常状态码
    PRODUCT_NOT_FOUND("20000","产品不存在（测试使用）"),
    //参数错误
    PARAM_NOT_VALID("10001","参数错误"),
    ;

    //异常码
    private String errorCode;
    //错误信息
    private String errorMessage;
}
