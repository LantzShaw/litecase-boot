package com.litecase.boot.web.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("user")
public class User {
    private String userName;

    private Integer gender;

    private Integer age;
}
