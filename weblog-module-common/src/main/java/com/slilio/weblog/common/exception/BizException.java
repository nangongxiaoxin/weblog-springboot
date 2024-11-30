package com.slilio.weblog.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizException extends RuntimeException{
    //异常码
    private String errCode;
    //错误信息
    private String errMessage;

    public BizException(BaseExceptionInterface baseExceptionInterface){
        this.errCode = baseExceptionInterface.getErrorCode();
        this.errMessage = baseExceptionInterface.getErrorMessage();
    }
}
