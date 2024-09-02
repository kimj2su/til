package com.jisu.taskdecorator.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

public class WebInterceptor implements HandlerInterceptor {


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    String test = request.getParameter("test");
    ThreadLocalStorage.setParameter(test);
    MDC.put("test", test);
    return true;
  }


  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) {
    MDC.clear();
  }

}
