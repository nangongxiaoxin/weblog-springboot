package com.slilio.weblog.web;

import com.slilio.weblog.common.domain.dos.UserDO;
import com.slilio.weblog.common.domain.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


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

//数据库插入测试
    @Autowired
    private UserMapper userMapper;
    @Test
    void insertTest(){
        //构建实体类
        UserDO userDO = UserDO.builder()
                .username("slilio")
                .password("123456")
                .createTime(new Date())
                .updateTime(new Date())
                .isDeleted(false)
                .build();
        userMapper.insert(userDO);

    }
}
