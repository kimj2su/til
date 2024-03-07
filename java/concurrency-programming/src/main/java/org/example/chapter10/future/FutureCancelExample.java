package org.example.chapter10.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureCancelExample {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Callable<Integer> callable = () -> {
            System.out.println("비동기 작업 시작..");
            Thread.sleep(2000);
            System.out.println("비동기 작업 끝..");

            return 2;
        };

        Future<Integer> future = executorService.submit(callable);

        Thread.sleep(1000);
        // future.cancel(true); // 작업 취소 인터럽트를 걸음
        future.cancel(false); // 작업 취소 인터럽트를 걸지 않음

        try {
            System.out.println("future.get() = " + future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
