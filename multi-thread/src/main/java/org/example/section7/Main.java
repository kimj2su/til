package org.example.section7;

import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();

    }

    public void method() {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        try {
            // do something
        } finally {
            reentrantLock.unlock();
        }

    }
}
