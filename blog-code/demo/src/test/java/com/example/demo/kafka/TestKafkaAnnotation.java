//package com.example.demo.kafka;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.example.demo.kafka.avro.PaymentMethod;
//import com.example.demo.kafka.avro.Recipient;
//import com.example.demo.kafka.avro.Sender;
//import com.example.demo.kafka.avro.Shipment;
//import java.util.Arrays;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.test.EmbeddedKafkaBroker;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//
//@ActiveProfiles("test")
//@SpringBootTest
////@Import({EmbeddedKafkaCreator.class})
//@EmbeddedKafka(
//    partitions = 1,
//    brokerProperties = {
//        "listeners=PLAINTEXT://localhost:9092",
//        "port=9092"
//    }
//)
//@DirtiesContext
//public class TestKafkaAnnotation {
//
//  @Autowired
//  private KafkaConsumer consumer;
//
//  @Autowired
//  private KafkaProducer producer;
//  @Autowired
//  KafkaTemplate<String, Shipment> kafkaTemplate;
//
//  @Value("${test.topic}")
//  private String topic;
//
//  @Autowired
//  private EmbeddedKafkaBroker embeddedKafkaBroker;
//
//  @Test
//  void test() {
//    Shipment shipment = Shipment.newBuilder()
//        .setSender(new Sender("John Doe", "123 Main St", "123", "123-456-7890"))
//        .setRecipient(new Recipient("Jane Doe", "456 Elm St", "456", "456-789-0123"))
//        .setContents(Arrays.asList("Item1", "Item2"))
//        .setCharge(100L)
//        .setPaymentMethod(PaymentMethod.Cash)
//        .build();
//    producer.send("embedded-test-topic", shipment);
//    kafkaTemplate.send("embedded-test-topic", shipment);
//
//    assertThat(consumer.getPayload()).isEqualTo(shipment.getSender().getName());
//  }
//
//}
