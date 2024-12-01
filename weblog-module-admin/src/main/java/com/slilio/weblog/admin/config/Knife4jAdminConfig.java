package com.slilio.weblog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@Profile("dev")
public class Knife4jAdminConfig {

    @Bean("adminApi")
    public Docket createApiDoc(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                //分组名称
                .groupName("Admin 后台接口")
                .select()
                //这里指定 Controller 扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.slilio.com.weblog.admin.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 构建api信息
     * @return
     */
    private ApiInfo buildApiInfo(){
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("weblog 博客 Admin 后台接口文档") //标题
                .description("Weblog 是一款由 Spring Boot + Vue 3.2 + Vite 4.3 开发的前后端分离博客。") //描述
                .contact(new Contact("slilio", "slilio.com", "bx_tianhuan@163.com"))
                .version("1.0")
                .build();
        return apiInfo;
    }
}
