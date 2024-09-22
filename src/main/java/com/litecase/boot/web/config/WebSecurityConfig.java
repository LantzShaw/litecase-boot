//package com.litecase.boot.web.config;
//
//import com.litecase.boot.web.filter.JwtAuthenticationFilter;
//import com.litecase.boot.web.service.UserService;
//import jakarta.servlet.Filter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.intercept.AuthorizationFilter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.header.writers.StaticHeadersWriter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * 参考文章: https://howtodoinjava.com/spring-security/spring-security-tutorial/
// */
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//    @Autowired
//    JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        //  List<Filter> filterList = new ArrayList<Filter>();
//        //  return new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**"), filterList);
//
//        http.authorizeHttpRequests(requests -> requests
//                .requestMatchers("/auth/login", "/auth/register", "/auth/test").permitAll()
//                .requestMatchers("/swagger-ui.html").permitAll()
//                .requestMatchers("/admin").hasRole("admin")
//                .anyRequest().authenticated()
//        );
//
//        // 因为是 前后端分离 要关闭 csrf()
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.httpBasic(AbstractHttpConfigurer::disable);
//
//        // 不通过 session 获取 SecurityContext
//        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        http.logout(AbstractHttpConfigurer::disable)
////                .authenticationProvider(authenticationProvider -> )
////                .headers(headers -> headers.addHeaderWriter(new StaticHeadersWriter("Content-Type", "application/json")))
////                .headers(headers -> headers.addHeaderWriter(new StaticHeadersWriter("Content-Type", "application/json")))
////                        .contentSecurityPolicy(""))
//        ;
//
////        http.addFilterBefore(jwtAuthenticationFilter, AuthorizationFilter.class);
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
////    @Bean
////    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
////        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
////
//////        provider.setUserDetailsService();
////
////    }
//
////    @Bean
////    public UserDetailsService userDetailsService() {
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        //  var user = User.withUsername("Lantz").password("123456").authorities("admin").build();
////
////        manager.createUser(User
////                .withUsername("Lantz")
////                .password("123456")
////                .roles("admin")
////                .build()
////        );
////
////        return manager;
////    }
//
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
//
////import org.springframework.context.annotation.Bean;
////        import org.springframework.context.annotation.Configuration;
////        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////        import org.springframework.security.config.annotation.web.builders.WebSecurity;
////        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
////        import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
////        import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
////        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////        import org.springframework.security.crypto.password.PasswordEncoder;
////        import org.springframework.security.web.SecurityFilterChain;
////        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
////
////@Configuration
////@EnableWebSecurity
////public class SecurityConfig {
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                // 配置 URL 路径的安全策略
////                .authorizeRequests(authorizeRequests -> authorizeRequests
////                        .requestMatchers("/public/**").permitAll()  // 允许所有用户访问 /public 路径
////                        .requestMatchers("/admin/**").hasRole("ADMIN")  // 只有 ADMIN 角色才能访问 /admin 路径
////                        .anyRequest().authenticated()  // 其他所有路径都需要认证
////                )
////                // 配置表单登录
////                .formLogin(formLogin -> formLogin
////                        .loginPage("/login")  // 自定义登录页面
////                        .permitAll()  // 允许所有用户访问登录页面
////                )
////                // 配置注销
////                .logout(logout -> logout
////                        .permitAll()  // 允许所有用户执行注销
////                )
////                // 配置 CSRF 保护
////                .csrf(csrf -> csrf
////                        .disable()  // 根据需要启用或禁用 CSRF 保护
////                );
////
////        return http.build();
////    }
////
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();  // 使用 BCrypt 编码器
