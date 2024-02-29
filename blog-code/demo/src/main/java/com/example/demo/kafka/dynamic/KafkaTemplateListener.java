package com.example.demo.kafka.dynamic;

import java.util.concurrent.BlockingQueue;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;


public class KafkaTemplateListener implements MessageListener<String, String> {

  private final BlockingQueue<ConsumerRecord<String, String>> blockingQueue;

  public KafkaTemplateListener(BlockingQueue<ConsumerRecord<String, String>> blockingQueue) {
    this.blockingQueue = blockingQueue;
  }

  @Override
  public void onMessage(ConsumerRecord<String, String> record) {
    if (blockingQueue != null) {
      try {
        blockingQueue.offer(record);
        System.out.println("RECORD PROCESSING QUEUE: " + record);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("RECORD PROCESSING: " + record);
    }
  }
}

