package com.litecase.boot.web.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDTO {
    private boolean success;  // 登录是否成功
    private String message;   // 提示信息，如登录失败的原因
    private String token;     // 令牌（JWT 或其他身份验证机制）
    private String userId;    // 用户唯一标识符
    private List<String> roles; // 用户角色或权限
    private long expiration;  // 令牌或会话的过期时间戳
}
