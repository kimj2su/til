package com.example.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaConsumer {

    private String payload;
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "embedded-test-topic", groupId = "testGroup")
    public void consume(ConsumerRecord<?, ?> consumerRecord) {
        System.out.println("Consumed message: " + consumerRecord.value());
        payload = consumerRecord.toString();
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public String getPayload() {
        return payload;
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
