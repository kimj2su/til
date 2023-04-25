package com.example.app.config;

import com.example.app.config.filter.JwtTokenFilter;
import com.example.app.exception.CustomAuthenticationEntryPoint;
import com.example.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final UserService userService;
    @Value("${web.ignore.patters}")
    String[] patterns;

    @Value("${jwt.secret-key}")
    private String key;

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers(patterns);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                                .requestMatchers(HttpMethod.POST, "/api/*/users/join", "/api/*/users/login").permitAll()
//                .requestMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
        )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtTokenFilter(key, userService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;

        return http.build();
    }

}
