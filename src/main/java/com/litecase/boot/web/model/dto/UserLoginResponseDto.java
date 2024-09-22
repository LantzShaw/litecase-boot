package com.litecase.boot.web.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserLoginResponseDto {
    private String username;
    private String token;
    private List<String> roles = new ArrayList<>();


    public UserLoginResponseDto(String username, String token, List<String> roles) {
        this.username = username;
        this.token = token;
        this.roles = roles;
    }
}
