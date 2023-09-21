package com.litecase.boot.web.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Primary;

@TableName("user")
@Data
public class User {
    private String id;
    
    private String username;

    private String nickname;

    private String phoneNumber;

    private String email;

    private Integer gender;

    private Integer age;

    private String idMumber;

    private String avatar;

    private int status;
}
