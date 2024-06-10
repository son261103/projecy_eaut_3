package com.example.admingiadien.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/users/register", "/users/login", "/index", "/users/**").permitAll()
                        .requestMatchers("/assets/**", "/user/assets/**").permitAll()
                        .requestMatchers("/admin/login", "/admin/register", "/admin/registerAdmin").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("Admin") // Sử dụng "hasAuthority" không có tiền tố "ROLE_"
                        .requestMatchers("/users/**").hasAuthority("User")  // Sử dụng "hasAuthority" không có tiền tố "ROLE_"
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/users/login")
                        .defaultSuccessUrl("/defaul", true) // Đặt URL chuyển hướng sau khi đăng nhập thành công
                        .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/index")
                                .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
