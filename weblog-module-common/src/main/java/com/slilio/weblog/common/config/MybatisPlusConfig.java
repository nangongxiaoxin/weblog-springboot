package com.slilio.weblog.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.slilio.weblog.common.domain.mapper")
public class MybatisPlusConfig {
}
