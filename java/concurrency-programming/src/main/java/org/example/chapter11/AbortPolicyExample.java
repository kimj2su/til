package org.example.chapter11;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AbortPolicyExample {

    public static void main(String[] args) {

        int corePoolSize = 2;
        int maximumPoolSize = 2;
        long keepAliveTime = 0;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        int taskNum = 5;

        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(
                        corePoolSize,
                        maximumPoolSize,
                        keepAliveTime,
                        TimeUnit.SECONDS,
                        workQueue,
                        new ThreadPoolExecutor.AbortPolicy()
                );

        for (int i = 0; i < taskNum; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Thread Name: " + Thread.currentThread().getName() + ", TaskId: " + taskId + " is working...");
            });
        }
        executor.shutdown();
    }
}
