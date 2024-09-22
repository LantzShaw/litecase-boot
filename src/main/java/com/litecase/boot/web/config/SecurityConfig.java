package com.litecase.boot.web.config;


import com.litecase.boot.web.common.AuthEntryPointJwt;
import com.litecase.boot.web.config.service.UserDetailsServiceImpl;
import com.litecase.boot.web.filter.JwtAuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    DataSource dataSource;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }


//    @Bean
//    public CommandLineRunner initData(UserDetailsService userDetailsService) {
//        return args -> {
//            JdbcUserDetailsManager manager = (JdbcUserDetailsManager) userDetailsService;
//            UserDetails user1 = User.withUsername("user1")
//                    .password(passwordEncoder().encode("password1"))
//                    .roles("USER")
//                    .build();
//            UserDetails admin = User.withUsername("admin")
//                    //.password(passwordEncoder().encode("adminPass"))
//                    .password(passwordEncoder().encode("adminPass"))
//                    .roles("ADMIN")
//                    .build();
//
//            JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//            userDetailsManager.createUser(user1);
//            userDetailsManager.createUser(admin);
//        };
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
//        () -> new DaoAuthenticationProvider().setUserDetailsService(userDetailsService(dataSource()))
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers("/h2-console/**").permitAll()
//                        .requestMatchers("/auth/login", "/auth/register").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated());
        http.sessionManagement(
                session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)
        );
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));
        //http.httpBasic(withDefaults());
        http.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin
                )
        );
        http.csrf(AbstractHttpConfigurer::disable);

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


//    @Bean
//    AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService)
//            throws Exception {
////        http.getSharedObject(AuthenticationManagerBuilder.class)
////                .userDetailsService(userDetailsService(dataSource))
////                .passwordEncoder(passwordEncoder);
////
////        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
//
//        // return http.build();
//
//    }

//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
//        builder.userDetailsService(userDetailsService(dataSource)).passwordEncoder(passwordEncoder());
//
//        return builder.build();
//    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//
//        System.out.println("jdbcUserDetailsManager = " + jdbcUserDetailsManager + "dataSource" + dataSource);
//
//        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, 'true' as enabled FROM user WHERE username = ?");
//
//        // jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT u.username, a.authority FROM user u, authority a WHERE u.user_id = a.user_id AND username = ?");
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT u.username, a.authority_name FROM user u INNER JOIN authority a ON u.user_id = a.user_id WHERE username = ?");
//
//        return jdbcUserDetailsManager;
//
//        // return new JdbcUserDetailsManager(dataSource);
//    }
}

//
//import jakarta.servlet.FilterChain;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//
///**
// * 参考文章: https://blog.csdn.net/OLinOne/article/details/128100367
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
////    private final UserDetailsService userDetailsService;
//
////    public SecurityConfig(UserDetailsService userDetailsService) {
////        this.userDetailsService = userDetailsService;
////    }
//
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/public/**")
////                        .permitAll()
////                        .anyRequest().authenticated())
////                .formLogin((form) -> form.loginPage("/login").permitAll())
////                .logout((logout) -> logout.permitAll());
////
////        return http.build();
////    }
//
////    @Bean
////    public JwtAuthenticationFilter jwtAuthenticationFilter() {
////        return new JwtAuthenticationFilter();
////    }
//
////    @Bean
////    public FilterChain filterChain(HttpSecurity http) throws Exception {
//////        http.csrf()
//////                .disable()
//////                .authorizeHttpRequests()
//////                .requestMatchers("/ignore1")
//////                .addFilterBefore(jwtAuthenticationFilter(), JwtAuthenticationFilter.class);
//////
//////        return http.build();
////
////        return null;
////    }
//
////    @Bean
////    public WebSecurityCustomizer webSecurityCustomizer() {
////        return web -> web.ignoring().requestMatchers("/ignores1");
////    }
//
//    @Bean
////    @Order(Ordered.HIGHEST_PRECEDENCE) // 这个不知道是啥？
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/ignores1", "/public/**")
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated()
//                )
////                .formLogin(Customizer.withDefaults());
//                .formLogin(Customizer.withDefaults())
////                .formLogin(formLogin -> formLogin
////                        .loginPage("/login")
////                        .permitAll())
//                .logout(logout -> logout.permitAll()
//                );
//
//        return http.build();
//    }
//
//
////    @bean
////    AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService)
////            throws Exception {
////        return http.getSharedObject(AuthenticationManagerBuilder.class)
////                .userDetailsService(users())
////                .passwordEncoder(bCryptPasswordEncoder)
////                .and()
////                .build();
////    }
//
////    /**
////     * AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(http.getSharedObject(AuthenticationManagerBuilder.class));
////     * 上面报错参考文章: https://stackoverflow.com/questions/77504542/rewriting-a-spring-security-deprecated-authenticationmanager-httpsecurity/77507143#77507143
////     *
////     * @param http
////     * @return
////     * @throws Exception
////     */
////    @Bean
////    public AuthenticationManagerBuilder authenticationManagerBuilder(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
////
//////        return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
//////                .passwordEncoder(passwordEncoder);
////////        AuthenticationManagerBuilder authenticationManagerBuilder =
////////                new AuthenticationManagerBuilder(http.getSharedObject(AuthenticationManagerBuilder.class));
////        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//////
////////        AuthenticationManagerBuilder authenticationManagerBuilder =
////////                new AuthenticationManagerBuilder(http.getSharedObject(SecurityConfig.class).getAuthenticationManager());
//////
////        authenticationManagerBuilder
////                .inMemoryAuthentication()
////                .withUser("user")
////                .password("{noop}123456")  // {noop} is used to indicate no encoding
////                .roles("USER");
////
////        return authenticationManagerBuilder;
////    }
//
//    // Configure authentication here instead of redefining beans
////    @Bean
////    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
////        return http.getSharedObject(AuthenticationManagerBuilder.class)
////                .userDetailsService(userDetailsService)
////                .passwordEncoder(passwordEncoder)
////                .and()
////                .build();
////    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // Use BCryptPasswordEncoder for hashing passwords
//    }
//
//
//    // Chat-gpt
////    import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.builders.WebSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
////import org.springframework.security.web.SecurityFilterChain;
//
////    @Configuration
////    @EnableWebSecurity
////    public class SecurityConfig {
//
////        @Bean
////        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////            http
////                    .authorizeHttpRequests(authorizeRequests ->
////                            authorizeRequests
////                                    .requestMatchers("/ignores1").permitAll()  // Modern approach
////                                    .anyRequest().authenticated()
////                    )
////                    .formLogin(withDefaults());  // Configures form-based login
////            return http.build();
////        }
////    }
//
//
//}
//
//
////@Configuration
////@EnableWebSecurity
////public class SecurityConfig extends WebSecurityConfigurerAdapter {
////
////    @Bean
////    public JwtAuthenticationFilter jwtAuthenticationFilter() {
////        return new JwtAuthenticationFilter();
////    }
////
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .csrf().disable() // 禁用 CSRF
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT 不使用 session
////                .and()
////                .authorizeRequests()
////                .antMatchers("/api/auth/**").permitAll() // 开放认证接口
////                .anyRequest().authenticated() // 其他请求必须认证
////                .and()
////                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 添加 JWT 过滤器
////    }
////}