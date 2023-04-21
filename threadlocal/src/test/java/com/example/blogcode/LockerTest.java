package com.example.blogcode;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class LockerTest {

    private final Locker locker = new Locker();

    @Test
    void field() {
        log.info("main start");
        Runnable userA = () -> {
            locker.addStudent("지수1");
            locker.assignLocker();
        };

        Runnable userB = () -> {
            locker.addStudent("지수2");
            locker.assignLocker();
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
//        sleep(2000);
        sleep(100);
        threadB.start();

        sleep(3000);
        log.info("main exit");
    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
