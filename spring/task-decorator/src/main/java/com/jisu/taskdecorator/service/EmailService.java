package com.jisu.taskdecorator.service;

import com.jisu.taskdecorator.config.ThreadLocalStorage;
import java.util.concurrent.Executor;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class EmailService {

  private final Executor asyncTaskExecutor;
  private final ThreadPoolTaskExecutor defaultTaskExecutor;

  public EmailService(@Qualifier("asyncTaskExecutor") Executor asyncTaskExecutor,
      @Qualifier("defaultTaskExecutor") ThreadPoolTaskExecutor defaultTaskExecutor) {
    this.asyncTaskExecutor = asyncTaskExecutor;
    this.defaultTaskExecutor = defaultTaskExecutor;
  }

  @Async
  public void sendMail() {
    System.out.println("[sendMail] :: " + Thread.currentThread().getName());
    String test = MDC.get("test");
    System.out.println("MDC test = " + test);
    String parameter = ThreadLocalStorage.getParameter();
    System.out.println("ThreadLocal parameter = " + parameter);
    // 스레드의 정보 가져오기
    asyncTaskExecutor.execute(() -> {
      System.out.println("Async Task Executor - Core Pool Size: " + ((ThreadPoolTaskExecutor) asyncTaskExecutor).getCorePoolSize());
      System.out.println("Async Task Executor - Max Pool Size: " + ((ThreadPoolTaskExecutor) asyncTaskExecutor).getMaxPoolSize());
    });
//    System.out.println("Default Task Executor - Core Pool Size: " + asyncTaskExecutor.getCorePoolSize());
//    System.out.println("Default Task Executor - Max Pool Size: " + asyncTaskExecutor.getMaxPoolSize());
  }

  @Async("defaultTaskExecutor")
  public void sendMail2() {
    System.out.println("[sendMail] :: " + Thread.currentThread().getName());
    String test = MDC.get("test");
    System.out.println("MDC test = " + test);
    String parameter = ThreadLocalStorage.getParameter();
    System.out.println("ThreadLocal parameter = " + parameter);
    // 스레드의 정보 가져오기
    System.out.println("Default Task Executor - Core Pool Size: " + defaultTaskExecutor.getCorePoolSize());
    System.out.println("Default Task Executor - Max Pool Size: " + defaultTaskExecutor.getMaxPoolSize());
  }

  @Async("defaultTaskExecutor")
  public Future<String> sendMailWithFuture() throws InterruptedException {
    Thread.sleep(1500);
    System.out.println("[sendMail] :: " + Thread.currentThread().getName());
    return new AsyncResult<>(Thread.currentThread().getName());
  }

  @Async("messagingTaskExecutor")
  public void sendMailWithCustomThreadPool() {
    System.out.println("[messagingTaskExecutor] :: " + Thread.currentThread().getName());
  }
}