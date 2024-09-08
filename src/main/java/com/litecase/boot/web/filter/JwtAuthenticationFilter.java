package com.litecase.boot.web.filter;

import com.litecase.boot.web.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter implements Filter {
    // 使用Autowired需要将当前类设置为Spring 容器，可以使用@Component 或者@Bean
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        final String authorazationHeader = httpRequest.getHeader("Authorization");

        if (authorazationHeader != null && authorazationHeader.startsWith("Bearer ")
                && authorazationHeader.split(" ").length == 2) {

            try {
                final String token = authorazationHeader.split(" ")[1];
                // final String token = authorazationHeader.substring(7);
                //  final String username = (String) httpRequest.getSession().getAttribute("username");

                // final String username = jwtUtil.getUsernameFromToken(token);
                final String username = jwtUtil.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
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
                    }
                }
            } catch (Exception e) {
                log.info("Invalid token: {}", e.getMessage());
            }
        }
        //  else {
        //  httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        //  }

        // 继续过滤器链的处理
        filterChain.doFilter(httpRequest, httpResponse);
    }
}
