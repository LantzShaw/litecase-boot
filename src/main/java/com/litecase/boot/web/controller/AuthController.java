package com.litecase.boot.web.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.litecase.boot.web.common.ApiResponse;
import com.litecase.boot.web.common.R;
import com.litecase.boot.web.config.service.UserDetailsImpl;
import com.litecase.boot.web.model.dto.UserLoginDto;
import com.litecase.boot.web.model.dto.UserLoginResponseDto;
import com.litecase.boot.web.model.dto.UserRegisterDto;
import com.litecase.boot.web.model.entity.User;
import com.litecase.boot.web.service.UserService;
import com.litecase.boot.web.util.JwtUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    final UserService userService;
    final JwtUtils jwtUtils;
    final AuthenticationManager authenticationManager;
    final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<UserLoginResponseDto>> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        Authentication authentication;

        log.info("auth login: {}", userLoginDto);
        List<String> roles = new ArrayList<>();

        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));

            log.info("authentication: {}", authentication);
        } catch (AuthenticationException exception) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("message", "Bad credentials");
//            map.put("status", false);
//            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);

            log.error("error: {}", exception);

            return ApiResponse.errorWithStatus(404, "Not Found", HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        UserLoginResponseDto response = new UserLoginResponseDto(userDetails.getUsername(), jwtToken, roles);

        return ApiResponse.successWithStatus(response, HttpStatus.OK);
    }


    // @RequestParam("username") String username 可以这么使用
    // @RequestBody("username") String username 不能这么使用
    // @ModelAttribute
//    @PostMapping(value = "/login")
//    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserLoginDto userLoginDto) {
//        User user = userService.findUsername(userLoginDto.getUsername());
//
//        log.info("username: {}", user);
//
//        if (user == null) {
////            return R.error("登录失败，用户不存在");
//            Map<String, Object> map = new HashMap<String, Object>();
//
//            map.put("message", "登录失败，用户不存在");
//            map.put("status", false);
//
////            return ResponseEntity.ok(R.error("登录失败，用户不存在"));
//
////            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(ApiResponse.error(401, "Invalid credentials"));
//
//        }
//
//        log.info("user password: {}", user.getPassword());
//
//        if (!userLoginDto.getPassword().equals(user.getPassword())) {
//            log.info("登录失败: {}", jwtUtil.generateToken(userLoginDto.getUsername()));
//
//
////            return ResponseEntity.ok(R.error(("登录失败，密码错误")));
//
////            return R.success("登录失败，密码错误");
//
//            return ResponseEntity.ok(ApiResponse.success(jwtUtil.generateToken(userLoginDto.getUsername()), "Login Successfully"));
//
////            return ResponseEntity.ok(jwtUtil.generateToken(userLoginDto.getUsername()));
//
////            Map<String, Object> map = new HashMap<String, Object>();
////
////            map.put("message", "登录失败，密码错误");
////            map.put("status", false);
////
////            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
//
////            return "登录失败";
//        }
//
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        map.put("message", "登录成功");
//        map.put("status", false);
//
//        return null;
//
////        return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
//
////        return ResponseEntity.ok()
//
////        return ResponseEntity.ok(R.success("登录成功"));
//
////        return R.success(jwtUtil.generateToken(userLoginDto.getUsername()));
//    }

    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody UserRegisterDto userRegisterDto) {
//        User user = userService.findUsername(userRegisterDto.getUsername());
        User user = null;

        log.info("userRegisterDto: {}, user: {}", userRegisterDto, user);

        if (Objects.nonNull(user)) {
//            return R.error("用户已存在");
            return ApiResponse.errorWithStatus(402, "用户已存在", HttpStatus.NOT_FOUND);
        }

        try {
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User newUser = new User();

            log.info("userId: {}", newUser.getUserId());

            Long generatedId = IdWorker.getId();

            newUser.setUsername(userRegisterDto.getUsername());
            newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
//            newUser.setPassword(userRegisterDto.getPassword());
//            newUser.setUserId(generatedId);

            log.info("newUser: {}", newUser);

            userService.save(newUser);

//            userService.register(newUser);

            return ApiResponse.successWithStatus(newUser, HttpStatus.OK);
        } catch (Exception e) {

            log.error("Exception: {}", e.getMessage());
            return ApiResponse.errorWithStatus(402, e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //    produces = MediaType.APPLICATION_JSON_VALUE
    @GetMapping(value = "/test")
    public R<String> test() {
        return R.success("test");
    }
}
