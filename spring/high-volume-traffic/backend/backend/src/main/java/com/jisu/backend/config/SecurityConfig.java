package com.jisu.backend.config;

import com.jisu.backend.jwt.JwtUtil;
import com.jisu.backend.service.BlackListService;
import com.jisu.backend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final CustomUserDetailsService userDetailsService;
  private final BlackListService blackListService;

  public SecurityConfig(CustomUserDetailsService userDetailsService,
      BlackListService blackListService) {
    this.userDetailsService = userDetailsService;
    this.blackListService = blackListService;
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder())
        .and()
        .build();

  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtil)
      throws Exception {
    http.csrf(CsrfConfigurer::disable)
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/api/users/signUp",
                    "/api/users/login"
                ).permitAll()
                .anyRequest().authenticated()
        )
        .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService, blackListService),
            UsernamePasswordAuthenticationFilter.class)
    // .formLogin(Customizer.withDefaults())
    ;

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
