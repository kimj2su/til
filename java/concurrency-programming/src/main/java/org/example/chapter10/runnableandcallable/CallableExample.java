package org.example.chapter10.runnableandcallable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(1);

    Callable<Integer> runnable = () -> {
      System.out.println("Callable 작업 1 수행 중");
      System.out.println("Callable 작업 1 수행 완료");

      return 1;
    };

    Future<Integer> future = executorService.submit(runnable);
    try {
      Integer result = future.get();
      System.out.println("result = " + result);
    } catch (Exception e) {
      e.printStackTrace();
    }

    executorService.shutdown();
  }
}
