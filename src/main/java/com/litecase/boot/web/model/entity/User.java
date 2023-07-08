package com.litecase.boot.web.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Primary;

@TableName("user")
@Data
public class User {
    private String userId;
    
    private String userName;

    private Integer gender;

    private Integer age;
}
