package org.example.chapter10.futureandcallback;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureCallbackExample {

  interface Callback {
    void onComplete(int result);
  }

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(1);

    Callable<Integer> runnable = () -> {
      System.out.println("Callable 작업 1 수행 중");
      System.out.println("Callable 작업 1 수행 완료");

      return 1;
    };

    Future<Integer> future = executorService.submit(runnable);
    System.out.println("비동기 작업 시작");
    registerCallback(future, result -> {
      System.out.println("비동기 작업 결과: " + result);
    });

    executorService.shutdown();
  }

  private static void registerCallback(Future<Integer> future, Callback callback) {
    new Thread(() -> {
      int result = 0;
      try {
        result = future.get();
      } catch (Exception e) {
        e.printStackTrace();
      }
      callback.onComplete(result);
    }).start();
  }
}
