package org.example.chapter10.scheduledexecutorservice;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleAtFixedRateExample {

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

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        scheduledFuture.cancel(true);
        scheduledExecutorService.shutdown();
    }
}
