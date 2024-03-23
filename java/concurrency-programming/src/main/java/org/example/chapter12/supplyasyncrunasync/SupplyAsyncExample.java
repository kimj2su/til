package org.example.chapter12.supplyasyncrunasync;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class SupplyAsyncExample {
    public static void main(String[] args) throws InterruptedException {
        MyService myService = new MyService();
        CompletableFuture<List<Integer>> cf = CompletableFuture.supplyAsync(new Supplier<List<Integer>>() {
            @Override
            public List<Integer> get() {
                System.out.println(Thread.currentThread().getName() + " 가 비동기 작업을 시작합니다.");
                return myService.getData();
            }
        });

        List<Integer> result = cf.join();
        result.stream().forEach(System.out::println);

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<List<Integer>> future = executorService.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " 가 비동기 작업을 시작합니다.");
            return myService.getData();
        });

        try {
            List<Integer> result2 = future.get();
            result2.stream().forEach(System.out::println);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}

class MyService {
    public List<Integer> getData() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return List.of(1, 2, 3, 4, 5);
    }
}
