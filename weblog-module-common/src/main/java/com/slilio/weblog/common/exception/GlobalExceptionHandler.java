package com.slilio.weblog.common.exception;

import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获自定义异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({BizException.class})
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest request,BizException e){
        log.warn("{} request fail,errorCode: {},errorMessage: {}",request.getRequestURL(),e.getErrCode(),e.getErrMessage());
        return Response.fail(e);
    }

    /**
     * 其他类型错误
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response<Object> handleOtherException(HttpServletRequest request,Exception  e){
        log.error("{} request error, ",request.getRequestURL(), e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }

}
