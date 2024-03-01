package org.example.chapter11;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorExample {
    public static void main(String[] args) {

        int corePoolSize = 2; // 기본 스레드 풀 크기
        int maximumPoolSize = 4; // 최대 스레드 풀 크기
        long keepAliveTime = 10; // 초 단위의 스레드 유지 시간
        // BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(4);
        int taskNum = 7; // 작업의 개수

        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

        for (int i = 0; i < taskNum; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Thread Name: " + Thread.currentThread().getName() + ", TaskId: " + taskId + " is working...");
            });
        }
        executor.shutdown();
    }
}
