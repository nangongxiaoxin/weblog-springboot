package com.slilio.weblog.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class WeblogWebApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testLog(){
        log.info("这是一行info日志");
        log.warn("这是一行warn日志");
        log.error("这是一行error日志");
        log.info("----{}",123);
    }
}
