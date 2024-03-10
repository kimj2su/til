package org.example.chapter10.executorservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class InvokeAllExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List< Callable<Integer>> tasks = new ArrayList<>();

        tasks.add(() -> {
            Thread.sleep(1000);
            return 1;
        });

        tasks.add(() -> {
            Thread.sleep(2000);
            return 2;
        });

        tasks.add(() -> {
            Thread.sleep(3000);
            throw new RuntimeException("Invoke All Error");
        });

        long started = 0;
        try {
            started = System.currentTimeMillis();
            List<Future<Integer>> futures = executorService.invokeAll(tasks);
            for (Future<Integer> future : futures) {
                try {
                    // 순서 보장 됨
                    Integer result = future.get();
                    System.out.println("Result: " + result);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("총 시간 : " + (System.currentTimeMillis() - started));
        }

        executorService.shutdown();

    }
}
