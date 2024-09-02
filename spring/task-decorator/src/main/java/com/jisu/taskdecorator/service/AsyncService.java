package com.jisu.taskdecorator.service;

import com.jisu.taskdecorator.config.ThreadLocalStorage;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class AsyncService {

  private final EmailService emailService;
  private final ThreadPoolTaskExecutor asyncTaskExecutor;
  private final ThreadPoolTaskExecutor defaultTaskExecutor;


  public AsyncService(EmailService emailService, @Qualifier("asyncTaskExecutor") ThreadPoolTaskExecutor asyncTaskExecutor,
      @Qualifier("defaultTaskExecutor") ThreadPoolTaskExecutor defaultTaskExecutor) {
    this.emailService = emailService;
    this.asyncTaskExecutor = asyncTaskExecutor;
    this.defaultTaskExecutor = defaultTaskExecutor;
  }

  public void asyncCall_1()  {
    System.out.println("[asyncCall_1] :: " + Thread.currentThread().getName());
    emailService.sendMail();
    emailService.sendMailWithCustomThreadPool();
  }

  //외부 클래스의 정의된 빈 객체의 @Async 호출
  public void asyncCall_2()  {
    System.out.println("[asyncCall_2] :: " + Thread.currentThread().getName());
    emailService.sendMail2();
    emailService.sendMailWithCustomThreadPool();
  }

  public String asyncCallWithFuture() throws ExecutionException, InterruptedException {
    System.out.println("[asyncCallWithFuture] :: " + Thread.currentThread().getName());
    Future<String> resultFuture = emailService.sendMailWithFuture();
    while (true) {
      if (resultFuture.isDone()) {
        return resultFuture.get();
      }
    }
  }

  //인스턴스를 생성한후 호출 비동기 처리 안됨
  public void asyncCall_3() {
    System.out.println("[asyncCall_2] :: " + Thread.currentThread().getName());
    EmailService emailService = new EmailService(asyncTaskExecutor, defaultTaskExecutor);
    emailService.sendMail();
    emailService.sendMailWithCustomThreadPool();
  }

  //내부 메서드를 @Async 로 정의 후 호출
  public void asyncCall_4() {
    System.out.println("[asyncCall_3] :: " + Thread.currentThread().getName());
    sendMail();
    String test = MDC.get("test");
    System.out.println("test = " + test);
    String parameter = ThreadLocalStorage.getParameter();
    System.out.println("parameter = " + parameter);
  }

  @Async
  public void sendMail() {
    System.out.println("[asyncCall_3] :: " + Thread.currentThread().getName());
  }
}
