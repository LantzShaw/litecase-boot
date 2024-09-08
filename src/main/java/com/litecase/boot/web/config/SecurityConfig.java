package com.litecase.boot.web.config;

import com.litecase.boot.web.filter.JwtAuthenticationFilter;
import jakarta.servlet.FilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;


/**
 * 参考文章: https://blog.csdn.net/OLinOne/article/details/128100367
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public FilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/ignore1")
//                .addFilterBefore(jwtAuthenticationFilter(), JwtAuthenticationFilter.class);
//
//        return http.build();

        return null;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/ignores1");
    }
}


//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // 禁用 CSRF
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 不使用 session
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/auth/**").permitAll() // 开放认证接口
//                .anyRequest().authenticated() // 其他请求必须认证
//                .and()
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 添加 JWT 过滤器
//    }
//}