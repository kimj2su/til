package org.example.chapter10.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteExample {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 작업 제출과 작업 실행을 분리하자 해서 사용함.
        executorService.execute(() -> {
            System.out.println("비동기 작업 실행");
        });

        // 위와 같은 코드
        // new Thread(() -> {
        //     System.out.println("비동기 작업 실행");
        // }).start();

        executorService.shutdown();
    }
}
