package com.slilio.weblog.common.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) //何时生效
@Target({ElementType.METHOD}) //何地使用
@Documented //生成文档时被保留
public @interface ApiOperationLog {

    /**
     * api 功能描述
     * @return
     */
    String description() default "";
}
