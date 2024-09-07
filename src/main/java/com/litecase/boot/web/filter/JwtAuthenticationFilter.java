package com.litecase.boot.web.filter;

import com.litecase.boot.web.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter implements Filter {
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        final String authorazationHeader = httpRequest.getHeader("Authorization");

        if (authorazationHeader != null && authorazationHeader.startsWith("Bearer ")
                && authorazationHeader.split(" ").length == 2) {
            final String token = authorazationHeader.split(" ")[1];

            if (jwtUtil.validateToken(token, (String) httpRequest.getSession().getAttribute("username"))) {
                if (jwtUtil.isTokenExpired(token)) {
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                } else {
                    filterChain.doFilter(httpRequest, httpResponse);
                }
            }
        } else {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }
}
