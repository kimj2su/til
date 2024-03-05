package org.example.chapter10.runnableandcallable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableExample {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(1);

    Runnable runnable = () -> {
      System.out.println("Runnable 작업 1 수행 중");
      System.out.println("Runnable 작업 1 수행 완료");
    };

    executorService.execute(runnable);

    executorService.shutdown();
  }
}
