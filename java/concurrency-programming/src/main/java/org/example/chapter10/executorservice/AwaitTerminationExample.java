package org.example.chapter10.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class AwaitTerminationExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });

        executorService.submit(() -> {
            while (true) {
                System.out.println("데몬 스레드 실행 중..");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("인터럽트 발생");
                    break;
                }
            }
        });

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("메인 스레드 종료");
    }
}
