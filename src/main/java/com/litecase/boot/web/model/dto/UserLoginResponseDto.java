package com.litecase.boot.web.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserLoginResponseDto {
    private String token;
    private List<String> roles = new ArrayList<>();
}
