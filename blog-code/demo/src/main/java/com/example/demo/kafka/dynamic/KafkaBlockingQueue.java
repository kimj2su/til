package com.example.demo.kafka.dynamic;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
public class KafkaBlockingQueue {
  private BlockingQueue<ConsumerRecord<String, String>> blockingQueue =
      new LinkedBlockingQueue<>();

  public BlockingQueue<ConsumerRecord<String, String>> getBlockingQueue() {
    return blockingQueue;
  }
}
