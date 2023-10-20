package org.example.section6;

import java.util.Random;

public class Main {

    public void example1() {
        Metrics metrics = new Metrics();
        BusinessLogin businessLogin = new BusinessLogin(metrics);
        BusinessLogin businessLogin2 = new BusinessLogin(metrics);

        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);

        businessLogin.start();
        businessLogin2.start();
        metricsPrinter.start();
    }

    public void example2() {
        DataRace dataRace = new DataRace();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                dataRace.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                dataRace.checkForDataRace();
            }
        });

        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) {
        Main main = new Main();
        // main.example1();
        main.example2();
    }

    public static class DataRace {
        volatile int x = 0;
        volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if (y > x) {
                System.out.println("This should not be possible!");
            }
        }
    }

    public static class MetricsPrinter extends Thread {
        private Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                double currentAverage = metrics.getAverage();
                System.out.println("Current average is: " + currentAverage);
            }
        }
    }
    public static class BusinessLogin extends Thread {
        private Metrics metrics;
        private Random random = new Random();

        public BusinessLogin(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                }
                long end = System.currentTimeMillis();

                metrics.addSample(end - start);
            }
        }
    }
    public static class Metrics {
        private long count = 0;
        private volatile double average = 0.0;

        public synchronized void addSample(long sample) {
            double currentSum = average * count;
            count++;
            average = (currentSum + sample) / count;
        }

        public double getAverage() {
            return average;
        }
    }
}
