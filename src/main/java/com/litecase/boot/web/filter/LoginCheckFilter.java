package com.litecase.boot.web.filter;

import com.alibaba.fastjson2.JSON;
import com.litecase.boot.web.common.BaseContext;
import com.litecase.boot.web.common.R;
import com.litecase.boot.web.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.yaml.snakeyaml.scanner.Scanner;

import java.io.IOException;

// NOTE: 需要在启动类中添加@ComponentScan注解

@Slf4j
// @Component
//@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        // HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        // 不需要处理请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout"
        };

        boolean check = check(urls, requestURI);

        if (check) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getSession().getAttribute("employee") != null) {
            Long employeeId = (Long) request.getSession().getAttribute("employee");

            BaseContext.setCurrentId(employeeId);

            filterChain.doFilter(request, response);
            return;
        }

        if (request.getSession().getAttribute("user") != null) {
            Long userId = (Long) request.getSession().getAttribute("user");

            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request, response);

            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOLOGIN")));

        log.info("拦截器");
        return;
    }

    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);

            if (match) {
                return true;
            }
        }

        return false;
    }
}
