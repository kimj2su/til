package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // 모든 경로 허용
//                .allowedOriginPatterns("http://localhost:*")
        .allowedOriginPatterns("https://39f3-120-50-90-162.ngrok-free.app")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*")
        .allowCredentials(true) // 자격 증명 허용
        .maxAge(3600); // preflight 요청 캐시 시간
  }

}