package com.jisu.taskdecorator.config;

import java.util.Map;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

public class CustomTaskDecorator implements TaskDecorator {

  @Override
  public Runnable decorate(Runnable runnable) {
    String parameter = ThreadLocalStorage.getParameter();
    Map<String, String> callerThreadContext = MDC.getCopyOfContextMap();
    return () -> {
        ThreadLocalStorage.setParameter(parameter);
        MDC.setContextMap(callerThreadContext);
        runnable.run();
    };
  }

}
