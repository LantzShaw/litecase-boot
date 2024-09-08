package com.litecase.boot.web.config;

import jakarta.servlet.FilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * 参考文章: https://blog.csdn.net/OLinOne/article/details/128100367
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }

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

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring().requestMatchers("/ignores1");
//    }

    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE) // 这个不知道是啥？
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/ignores1")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
//                .formLogin(Customizer.withDefaults());
                .formLogin(Customizer.withDefaults())
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login")
//                        .permitAll())
                .logout(logout -> logout.permitAll()
                );

        return http.build();
    }


//    @bean
//    AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService)
//            throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(users())
//                .passwordEncoder(bCryptPasswordEncoder)
//                .and()
//                .build();
//    }

    /**
     * AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(http.getSharedObject(AuthenticationManagerBuilder.class));
     * 上面报错参考文章: https://stackoverflow.com/questions/77504542/rewriting-a-spring-security-deprecated-authenticationmanager-httpsecurity/77507143#77507143
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManagerBuilder authenticationManagerBuilder(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {

//        return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder);
////        AuthenticationManagerBuilder authenticationManagerBuilder =
////                new AuthenticationManagerBuilder(http.getSharedObject(AuthenticationManagerBuilder.class));
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//
////        AuthenticationManagerBuilder authenticationManagerBuilder =
////                new AuthenticationManagerBuilder(http.getSharedObject(SecurityConfig.class).getAuthenticationManager());
//
        authenticationManagerBuilder
                .inMemoryAuthentication()
                .withUser("user")
                .password("{noop}123456")  // {noop} is used to indicate no encoding
                .roles("USER");

        return authenticationManagerBuilder;
    }

    // Configure authentication here instead of redefining beans
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder)
//                .and()
//                .build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCryptPasswordEncoder for hashing passwords
    }


    // Chat-gpt
//    import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.SecurityFilterChain;

//    @Configuration
//    @EnableWebSecurity
//    public class SecurityConfig {

//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            http
//                    .authorizeHttpRequests(authorizeRequests ->
//                            authorizeRequests
//                                    .requestMatchers("/ignores1").permitAll()  // Modern approach
//                                    .anyRequest().authenticated()
//                    )
//                    .formLogin(withDefaults());  // Configures form-based login
//            return http.build();
//        }
//    }


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