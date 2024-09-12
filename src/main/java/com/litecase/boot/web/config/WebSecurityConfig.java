package com.litecase.boot.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


/**
 * 参考文章: https://howtodoinjava.com/spring-security/spring-security-tutorial/
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        var user = User.withUsername("Lantz").password("123456").authorities("admin").build();

        manager.createUser(User
                .withUsername("lantzshaw@163.com")
                .password("123456")
                .roles("user")
                .build()
        );

        return manager;
    }
}
