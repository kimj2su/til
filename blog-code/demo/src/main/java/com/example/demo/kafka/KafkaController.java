package com.example.demo.kafka;

import com.example.demo.kafka.avro.Shipment;
import com.example.demo.kafka.dynamic.KafkaBlockingQueue;
import com.example.demo.kafka.dynamic.KafkaListenerCreator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

  private final KafkaTemplate<String, Shipment> kafkaTemplate;
  private final KafkaTemplate<String, String> kafkaTemplateForString;
  private final KafkaListenerCreator kafkaListenerCreator;
  private final KafkaBlockingQueue kafkaBlockingQueue;
  public KafkaController(KafkaTemplate<String, Shipment> kafkaTemplate,
      KafkaTemplate<String, String> kafkaTemplateForString,
      KafkaListenerCreator kafkaListenerCreator, KafkaBlockingQueue kafkaBlockingQueue) {
    this.kafkaTemplate = kafkaTemplate;
    this.kafkaTemplateForString = kafkaTemplateForString;
    this.kafkaListenerCreator = kafkaListenerCreator;
    this.kafkaBlockingQueue = kafkaBlockingQueue;
  }

  @PostMapping("/send")
  public String send(@RequestBody Shipment shipment) {
    kafkaTemplate.send("embedded-test-topic", shipment);
    return "Published successfully";
  }

  @PostMapping("/create")
  public void create(@RequestParam String topic) {
    kafkaListenerCreator.createAndRegisterListener(topic);
  }

  @PostMapping("/create2")
  public void create2(@RequestParam String topic) {
    kafkaListenerCreator.createAndRegisterListenerWithQueue(topic, kafkaBlockingQueue.getBlockingQueue());
  }

  @PostMapping(path = "/send2")
  public void send(@RequestParam String topic, @RequestParam String message) {
    kafkaTemplateForString.send(topic, message);
  }
}
