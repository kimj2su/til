package org.example.section2;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new NewThread());
        Thread thread2 = new Thread(() -> System.out.println("Start Thread2"));

        thread.start();
        thread2.start();
        thread2.join();
        thread.join();

        System.out.println("Start Main");

    }

    private static class NewThread implements Runnable {
        @Override
        public void run() {
            System.out.println("Start Thread1");
        }
    }
}
