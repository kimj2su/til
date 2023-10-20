package org.example.section5;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("We currently have " + inventoryCounter.getItems() + " items");
    }

    private static class DecrementingThread extends Thread {
        private final InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    private static class IncrementingThread extends Thread {
        private final InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    private static class InventoryCounter {
        private int items = 0;

        // public synchronized void increment() {
        //     items++;
        // }
        //
        // public synchronized void decrement() {
        //     items--;
        // }

        public void increment() {
            synchronized (this) {
                items++;
            }
        }

        public synchronized void decrement() {
            synchronized (this) {
                items--;
            }
        }

        public int getItems() {
            return items;
        }
    }
}
