package com.example.demo.kafka.dynamic;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
public class RecordHandler {

  private final KafkaBlockingQueue kafkaBlockingQueue;

  public RecordHandler(KafkaBlockingQueue kafkaBlockingQueue) {
    this.kafkaBlockingQueue = kafkaBlockingQueue;
    this.consume();
  }

  public void consume() {
    Thread consumerThread = new Thread(() -> {
      while (true) {
        try {
          ConsumerRecord<String, String> record = kafkaBlockingQueue.getBlockingQueue().take();
          handle(record);
        } catch (InterruptedException e) {
          System.out.println("============" + e.getLocalizedMessage());
          Thread.currentThread().interrupt();
        } catch (Exception e) {
          System.out.println("Ignore exception. error={}" +  e.getLocalizedMessage());
        }
      }
    });
    consumerThread.start();
  }

  private void handle(ConsumerRecord<String, String> record) {
//    if (!record.value().startsWith("[logging]")){
//      return;
//    }

    System.out.println("Received message: " + record.value());
  }
}
