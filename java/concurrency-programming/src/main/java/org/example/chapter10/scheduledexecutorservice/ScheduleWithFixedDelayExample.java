package org.example.chapter10.scheduledexecutorservice;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleWithFixedDelayExample {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

        Runnable task = () -> {
            try {
                Thread.sleep(2000);
                System.out.println("Thread: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        // 이전 작업이 끝나야 다음 작업을 실행한다. delay가 끝난 이후에 실행한다.
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(task, 1, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        scheduledFuture.cancel(true);
        scheduledExecutorService.shutdown();
    }
}
