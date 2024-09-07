package com.litecase.boot.web.controller;

import com.litecase.boot.web.common.R;
import com.litecase.boot.web.mapper.UserMapper;
import com.litecase.boot.web.model.dto.UserDto;
import com.litecase.boot.web.model.entity.User;
import com.litecase.boot.web.service.UserService;
import com.litecase.boot.web.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;


    // @RequestParam("username") String username 可以这么使用
    // @RequestBody("username") String username 不能这么使用
    // @ModelAttribute
    @PostMapping("/login")
    public R<String> login(@RequestBody UserDto userDto) {
        User user = userService.findUsername(userDto.getUsername());

        if(user == null) {
            return R.success("登录失败，用户不存在");
        }

        if(!userDto.getPassword().equals(user.getPassword())) {
            return R.success("登录失败，密码错误");
        }

        return R.success(jwtUtil.generateToken(userDto.getUsername()));
    }
}
