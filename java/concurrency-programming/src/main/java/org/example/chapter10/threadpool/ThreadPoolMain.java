package org.example.chapter10.threadpool;

public class ThreadPoolMain {

  public static void main(String[] args) throws InterruptedException {
    SimpleThreadPool simpleThreadPool = new SimpleThreadPool(3);

    for (int i = 0; i < 10; i++) {
      int taskId = i;
      simpleThreadPool.submit(() -> {
        System.out.println(Thread.currentThread().getName() + ": 작업 " + taskId + " 수행중..");
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        System.out.println("TaskId: " + taskId + " is done...");
      });
    }

    Thread.sleep(3000);

    simpleThreadPool.shutDown();
  }

}
