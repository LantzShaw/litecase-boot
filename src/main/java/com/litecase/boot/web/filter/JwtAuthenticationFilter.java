package com.litecase.boot.web.filter;

import com.alibaba.fastjson2.JSON;
import com.litecase.boot.web.model.entity.User;
import com.litecase.boot.web.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate redisTemplate;

    // 使用Autowired需要将当前类设置为Spring 容器，可以使用@Component 或者@Bean
    @Autowired
    private JwtUtil jwtUtil;
    // private final JwtUtil jwtUtil;

//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//
//        final String authorazationHeader = httpRequest.getHeader("Authorization");
//
//        if (authorazationHeader != null && authorazationHeader.startsWith("Bearer ")
//                && authorazationHeader.split(" ").length == 2) {
//
//            try {
//                final String token = authorazationHeader.split(" ")[1];
//                // final String token = authorazationHeader.substring(7);
//                // final String username = (String) httpRequest.getSession().getAttribute("username");
//
//                // final String username = jwtUtil.getUsernameFromToken(token);
//                final String username = jwtUtil.extractUsername(token);
//
//                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    if (jwtUtil.validateToken(token, username)) {
//                        // 获取 Authentication 对象
//                        // Authentication authentication = jwtUtil.getAuthentication(token);
//                        // SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                        //  if (jwtUtil.isTokenExpired(token)) {
//                        //  httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
//                        //  } else {
//                        //  filterChain.doFilter(httpRequest, httpResponse);
//                        //  }
//
//                        //  SecurityContextHolder.getContext().setAuthentication();
//
//                        // 继续过滤器链的处理
//                        filterChain.doFilter(httpRequest, httpResponse);
//                        return;
//                    }
//                }
//            } catch (Exception e) {
//                log.info("Invalid token: {}", e.getMessage());
//            }
//        }
//
////        throw new AccessDeniedException("Access denied");
//
//        // 继续过滤器链的处理
//        filterChain.doFilter(httpRequest, httpResponse);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorazationHeader = request.getHeader("Authorization");

        if (authorazationHeader != null && authorazationHeader.startsWith("Bearer ")
                && authorazationHeader.split(" ").length == 2) {

            try {
                final String token = authorazationHeader.split(" ")[1];
                // final String token = authorazationHeader.substring(7);
                // final String username = (String) httpRequest.getSession().getAttribute("username");

                // final String username = jwtUtil.getUsernameFromToken(token);
                final String username = jwtUtil.extractUsername(token);
                final String redisUsername = (String) redisTemplate.opsForValue().get(String.valueOf(username));

//                if (!StringUtils.hasText(redisUsername)) {
//                    filterChain.doFilter(httpRequest, httpResponse);
//                    return;
//                }


                log.info("The token: {}", token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    log.info("Jwt authentication filter: {}", username);


                    if (jwtUtil.validateToken(token, username)) {
                        // 获取 Authentication 对象
                        // Authentication authentication = jwtUtil.getAuthentication(token);
                        // SecurityContextHolder.getContext().setAuthentication(authentication);

                        //  if (jwtUtil.isTokenExpired(token)) {
                        //  httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                        //  } else {
                        //  filterChain.doFilter(httpRequest, httpResponse);
                        //  }

                        //  SecurityContextHolder.getContext().setAuthentication();

                        User user = JSON.parseObject(redisUsername, User.class);
//
//                        // 将用户信息存放在SecurityContextHolder.getContext()，后面的过滤器就可以获得用户信息了。这表明当前这个用户是登录过的，后续的拦截器就不用再拦截了
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        // 继续过滤器链的处理
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
            } catch (Exception e) {
                log.error("Invalid token: {}", e.getMessage());
            }

            filterChain.doFilter(request, response);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return true;
    }
}
