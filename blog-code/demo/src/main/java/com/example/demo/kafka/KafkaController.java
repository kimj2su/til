package com.example.demo.kafka;

import com.example.demo.kafka.avro.Shipment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

  private final KafkaTemplate<String, Shipment> kafkaTemplate;

  public KafkaController(KafkaTemplate<String, Shipment> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @PostMapping("/send")
  public String send(@RequestBody Shipment shipment) {
    kafkaTemplate.send("embedded-test-topic", shipment);
    return "Published successfully";
  }
}
