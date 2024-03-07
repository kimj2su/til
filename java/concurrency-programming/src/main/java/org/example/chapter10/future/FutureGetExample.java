package org.example.chapter10.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureGetExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Callable<Integer> callable = () -> {
            System.out.println("비동기 작업 시작..");
            Thread.sleep(2000);
            System.out.println("비동기 작업 끝..");

            return 2;
        };

        Future<Integer> future = executorService.submit(callable);

        while (!future.isDone()) { // 작업이 끝났는지 확인 -> 동기
            System.out.println("작업을 기다리는 중..");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            System.out.println("future.get() = " + future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
