package org.example.chapter12.supplyasyncrunasync;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RunAsyncExample {
    public static void main(String[] args) throws InterruptedException {
        MyService2 myService = new MyService2();
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
                System.out.println(Thread.currentThread().getName() + " 가 비동기 작업을 시작합니다.");
                myService.getData().forEach(System.out::println);
        });

        cf.join();

        System.out.println("메인 스레드 종료");

    }
}

class MyService2 {
    public List<Integer> getData() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return List.of(1, 2, 3, 4, 5);
    }
}
