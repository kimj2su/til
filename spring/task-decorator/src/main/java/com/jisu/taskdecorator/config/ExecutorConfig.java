package com.jisu.taskdecorator.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public abstract class ExecutorConfig implements AsyncConfigurer {

  public abstract ThreadPoolTaskExecutor getThreadPoolTaskExecutor();

  @Bean(name = "asyncTaskExecutor", destroyMethod = "shutdown")
  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = getThreadPoolTaskExecutor();
    if (executor == null) {
      executor = new ThreadPoolTaskExecutor();
      executor.setCorePoolSize(200);
      executor.setMaxPoolSize(300);
      executor.setKeepAliveSeconds(10);
      executor.setThreadNamePrefix("asyncTaskExecutor-");
    }

    executor.setTaskDecorator(new CustomTaskDecorator());

    return executor;
  }
}
