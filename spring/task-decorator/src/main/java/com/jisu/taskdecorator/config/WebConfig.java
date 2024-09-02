package com.jisu.taskdecorator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    WebInterceptor webInterceptor = new WebInterceptor();
    registry.addInterceptor(webInterceptor);
  }
}
