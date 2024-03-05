package org.example.chapter10.futureandcallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {

  public static void main(String[] args) {

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    Future<Integer> future = executorService.submit(() -> {
      Thread.sleep(1000);
      return 1;
    });

    System.out.println("비동기 작업 시작");
    try {
      Integer value = future.get();
      System.out.println("비동기 작업 결과= " + value);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }

    executorService.shutdown();
  }

}
