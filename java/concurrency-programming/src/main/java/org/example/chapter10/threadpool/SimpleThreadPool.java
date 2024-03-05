package org.example.chapter10.threadpool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimpleThreadPool {

  private final int numThreads;
  private final Queue<Runnable> taskQueue;
  private final Thread[] threads;
  private volatile boolean isShutDown;

  public SimpleThreadPool(int numThreads) {
    this.numThreads = numThreads;
    this.taskQueue = new LinkedBlockingQueue<>();
    threads = new Thread[numThreads];
    this.isShutDown = false;

    for (int i = 0; i < numThreads; i++) {
      threads[i] = new WorkerThread();
      threads[i].start();
    }
  }

  public void submit(Runnable task) {
    if (!isShutDown) {
      synchronized (taskQueue) {
        taskQueue.offer(task);
        taskQueue.notifyAll();
      }
    }
  }

  public void shutDown() {
    isShutDown = true;
    synchronized (taskQueue) {
      taskQueue.notifyAll();
    }

    for (Thread thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
//        Thread.currentThread().interrupt();
      }
//      thread.interrupt();
    }
  }

  private class WorkerThread extends Thread {

    @Override
    public void run() {
      while (!isShutDown) {
        Runnable task;
        synchronized (taskQueue) {
          while (taskQueue.isEmpty() && !isShutDown) {
            try {
              taskQueue.wait();
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          }
        }
        if (!taskQueue.isEmpty()) {
          task = taskQueue.poll();
        } else {
          continue;
        }
        task.run();
      }
    }
  }
}
