package org.example.chapter10.executorservice;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SubmitRunnableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("비동기 작업 실행");
            }
        });

        Object result = future.get();
        System.out.println("result = " + result);

        Future<Integer> future2 = executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("비동기 작업 실행");
            }
        }, 100);

        Object result2 = future2.get();
        System.out.println("result 2= " + result2);

        executorService.shutdown();
    }
}
