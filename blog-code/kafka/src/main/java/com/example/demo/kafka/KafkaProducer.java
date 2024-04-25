package com.example.demo.kafka;

import com.example.demo.kafka.avro.Shipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, Shipment shipment) {
        LOGGER.info("sending payload='{}' to topic='{}'", shipment, topic);
        kafkaTemplate.send(topic, shipment);
    }
}

