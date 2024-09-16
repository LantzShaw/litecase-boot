package com.litecase.boot.web.model.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;  // 或 email，或者 loginId，视具体业务需求
    private String password;
    private Boolean rememberMe;  // 可选字段，表示是否保持登录状态
    private String captcha;  // 可选字段，验证码字段
}
