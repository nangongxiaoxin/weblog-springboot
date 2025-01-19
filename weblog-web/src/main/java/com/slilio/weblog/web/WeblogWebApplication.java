package com.slilio.weblog.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"com.slilio.weblog.*"}) // 多模块中，必需手动指定扫描 com.slilio.weblog 包下面的所有类
@EnableScheduling // 启用定时任务
public class WeblogWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(WeblogWebApplication.class, args);
  }
}
