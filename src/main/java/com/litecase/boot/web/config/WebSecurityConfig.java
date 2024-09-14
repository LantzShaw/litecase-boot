package com.litecase.boot.web.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;


/**
 * 参考文章: https://howtodoinjava.com/spring-security/spring-security-tutorial/
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        List<Filter> filterList = new ArrayList<Filter>();
//        return new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**"), filterList);

        httpSecurity.authorizeHttpRequests(requests -> requests.requestMatchers("/login").permitAll().anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").permitAll())
                .logout(logout -> logout.permitAll());

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        var user = User.withUsername("Lantz").password("123456").authorities("admin").build();

        manager.createUser(User
                .withUsername("Lantz")
                .password("123456")
                .roles("user")
                .build()
        );

        return manager;
    }



}

//
//import org.springframework.context.annotation.Bean;
//        import org.springframework.context.annotation.Configuration;
//        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//        import org.springframework.security.config.annotation.web.builders.WebSecurity;
//        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//        import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//        import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//        import org.springframework.security.crypto.password.PasswordEncoder;
//        import org.springframework.security.web.SecurityFilterChain;
//        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // 配置 URL 路径的安全策略
//                .authorizeRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers("/public/**").permitAll()  // 允许所有用户访问 /public 路径
//                        .requestMatchers("/admin/**").hasRole("ADMIN")  // 只有 ADMIN 角色才能访问 /admin 路径
//                        .anyRequest().authenticated()  // 其他所有路径都需要认证
//                )
//                // 配置表单登录
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login")  // 自定义登录页面
//                        .permitAll()  // 允许所有用户访问登录页面
//                )
//                // 配置注销
//                .logout(logout -> logout
//                        .permitAll()  // 允许所有用户执行注销
//                )
//                // 配置 CSRF 保护
//                .csrf(csrf -> csrf
//                        .disable()  // 根据需要启用或禁用 CSRF 保护
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();  // 使用 BCrypt 编码器
