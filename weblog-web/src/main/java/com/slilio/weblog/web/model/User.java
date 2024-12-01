package com.slilio.weblog.web.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@ApiModel(value = "用户实体类")
public class User {
    //用户名
    @NotNull(message = "用户名不能为空") //注解 用户名不能为空
    private String username;

    //性别
    @NotNull(message = "性别不能为空") //注解 性别不能为空
    private Integer sex;

    //年龄
    @NotNull(message = "年龄不能为空")
    @Min(value = 18,message = "年龄必须大于18") //年龄的大小注解
    @Max(value= 100,message = "年龄必须小于100")
    private Integer age;

    //邮箱
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    // 创建时间
    private LocalDateTime createTime;
    // 更新日期
    private LocalDate updateDate;
    // 时间
    private LocalTime time;
}
