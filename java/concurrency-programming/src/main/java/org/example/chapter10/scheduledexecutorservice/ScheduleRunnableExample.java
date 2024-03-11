package org.example.chapter10.scheduledexecutorservice;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleRunnableExample {

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("작업이 한 번 실행되고 결과를 반환한다.");

        // 결과를 가지고 사용하기 목적보단  일정 시간 후에 작업을 실행하고 싶을 때 사용한다.
        scheduledExecutorService.schedule(task, 3, TimeUnit.SECONDS);

        Thread.sleep(5000);

        scheduledExecutorService.shutdown();
    }
}
