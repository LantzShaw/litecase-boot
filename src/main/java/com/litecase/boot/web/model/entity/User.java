package com.litecase.boot.web.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.context.annotation.Primary;

@TableName("user")
@Data
public class User {
    @TableId(type = IdType.ASSIGN_ID)
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
