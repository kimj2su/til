package com.example.demo.kafka;

import com.example.demo.kafka.avro.Shipment;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaConsumer {
  private final TestRepository repository;

  public KafkaConsumer(TestRepository repository) {
    this.repository = repository;
  }

  @KafkaListener(topics = "embedded-test-topic", groupId = "jisu")
  public void consume(ConsumerRecord<String, Shipment> consumerRecord) {
    System.out.println("Consumed message: " + consumerRecord.value());
    repository.deleteAll();
  }

}
