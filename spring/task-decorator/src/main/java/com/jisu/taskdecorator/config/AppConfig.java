package com.jisu.taskdecorator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AppConfig extends ExecutorConfig {

  @Override
  public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);
    executor.setMaxPoolSize(10);
    executor.setKeepAliveSeconds(10);
    executor.setThreadNamePrefix("overrideAsyncTaskExecutor-");
    return executor;
  }

  @Bean(name = "defaultTaskExecutor", destroyMethod = "shutdown")
  public ThreadPoolTaskExecutor defaultTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(200); // 기본 스레드 수
    executor.setMaxPoolSize(300); // 최대 스레드 수
    executor.setKeepAliveSeconds(10); // 대기 시간
    executor.setQueueCapacity(100); // 대기 큐, 맥스이상의 업무가 몰려오면 큐 개수만큼은 저장해둘 수 있다는 뜻. 단, 그 이상이 들어오면 탈락시켜버린다.
    executor.setTaskDecorator(new CustomTaskDecorator());

    return executor;
  }

  @Bean(name = "messagingTaskExecutor", destroyMethod = "shutdown")
  public ThreadPoolTaskExecutor messagingTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(200);
    executor.setMaxPoolSize(300);

    return executor;
  }
}
