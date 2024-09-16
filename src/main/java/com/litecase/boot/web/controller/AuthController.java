package com.litecase.boot.web.controller;

import com.litecase.boot.web.common.ApiResponse;
import com.litecase.boot.web.common.R;
import com.litecase.boot.web.model.dto.UserLoginDto;
import com.litecase.boot.web.model.dto.UserRegisterDto;
import com.litecase.boot.web.model.entity.User;
import com.litecase.boot.web.service.UserService;
import com.litecase.boot.web.util.JwtUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
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
    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserLoginDto userLoginDto) {
        User user = userService.findUsername(userLoginDto.getUsername());

        log.info("username: {}", user);

        if (user == null) {
//            return R.error("登录失败，用户不存在");
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("message", "登录失败，用户不存在");
            map.put("status", false);

//            return ResponseEntity.ok(R.error("登录失败，用户不存在"));

//            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(401, "Invalid credentials"));

        }

        if (!userLoginDto.getPassword().equals(user.getPassword())) {
            log.info("登录失败: {}", jwtUtil.generateToken(userLoginDto.getUsername()));


//            return ResponseEntity.ok(R.error(("登录失败，密码错误")));

//            return R.success("登录失败，密码错误");

            return ResponseEntity.ok(ApiResponse.success(jwtUtil.generateToken(userLoginDto.getUsername()), "Login Successfully"));

//            return ResponseEntity.ok(jwtUtil.generateToken(userLoginDto.getUsername()));

//            Map<String, Object> map = new HashMap<String, Object>();
//
//            map.put("message", "登录失败，密码错误");
//            map.put("status", false);
//
//            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);

//            return "登录失败";
        }

        log.info("-------step1-------");


        Map<String, Object> map = new HashMap<String, Object>();

        map.put("message", "登录成功");
        map.put("status", false);

        return null;

//        return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);

//        return ResponseEntity.ok()

//        return ResponseEntity.ok(R.success("登录成功"));

//        return R.success(jwtUtil.generateToken(userLoginDto.getUsername()));
    }

    @PostMapping(value = "/register")
    public R<String> register(UserRegisterDto userRegisterDto) {
        User user = userService.findUsername(userRegisterDto.getUsername());

        if (Objects.nonNull(user)) {
            return R.error("用户已存在");
        }

        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User newUser = new User();

            newUser.setUsername(userRegisterDto.getUsername());
            newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
            userService.save(newUser);

            return R.success("用户注册成功");
        } catch (Exception e) {
            return R.error("用户注册失败");
        }
    }

    //    produces = MediaType.APPLICATION_JSON_VALUE
    @GetMapping(value = "/test")
    public R<String> test() {
        return R.success("test");
    }
}
