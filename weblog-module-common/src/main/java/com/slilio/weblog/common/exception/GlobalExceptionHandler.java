package com.slilio.weblog.common.exception;

import com.slilio.weblog.common.enums.ResponseCodeEnum;
import com.slilio.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获自定义异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({BizException.class})
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest request, BizException e) {
        log.warn("{} request fail,errorCode: {},errorMessage: {}", request.getRequestURL(), e.getErrCode(), e.getErrMessage());
        return Response.fail(e);
    }

    /**
     * 其他类型错误
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response<Object> handleOtherException(HttpServletRequest request, Exception e) {
        log.error("{} request error, ", request.getRequestURL(), e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Response<Object> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        //参数错误异常码
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();
        //获取BindingResult
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder sb = new StringBuilder();

        //获取校验不通过的字段，并且组合错误信息，格式为： email 邮箱格式不正确，当前值为：‘1243qq.com’;
        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent(errors -> {
            errors.forEach(error ->
                    sb.append(error.getField())
                            .append(" ")
                            .append(error.getDefaultMessage())
                            .append(",当前值为： '")
                            .append(error.getRejectedValue())
                            .append("'; ")
            );
        });

        //错误信息
        String errorMessage = sb.toString();

        log.warn("{} request error,errorCode: {}, errorMessage: {}", request.getRequestURL(), errorCode, errorMessage);
        return Response.fail(errorCode, errorMessage);
    }
}










