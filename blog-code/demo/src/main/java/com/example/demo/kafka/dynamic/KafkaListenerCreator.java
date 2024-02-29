package com.example.demo.kafka.dynamic;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Service;

/**
 * https://medium.com/@putnin.v/dynamic-kafka-listener-consumer-creation-with-spring-4f8f359d715e
 */
@Service
public class KafkaListenerCreator {
  private static final String kafkaGroupId = "kafkaGroupId";
  String kafkaListenerId = "kafkaListenerId-";
  private static final String kafkaGeneralListenerEndpointId = "kafkaListenerId-";
  static AtomicLong endpointIdIndex = new AtomicLong(1);

  private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
  private final KafkaListenerContainerFactory kafkaListenerContainerFactory;

  public KafkaListenerCreator(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
      KafkaListenerContainerFactory kafkaListenerContainerFactory) {
    this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
    this.kafkaListenerContainerFactory = kafkaListenerContainerFactory;
  }

  public void createAndRegisterListener(String topic) {
    KafkaListenerEndpoint listener = createKafkaListenerEndpoint(topic);
    kafkaListenerEndpointRegistry.registerListenerContainer(listener, kafkaListenerContainerFactory, true);
  }

  public void createAndRegisterListenerWithQueue(String topic, BlockingQueue<ConsumerRecord<String, String>> blockingQueue) {
    KafkaListenerEndpoint listener = createKafkaListenerEndpoint(topic, blockingQueue);
    kafkaListenerEndpointRegistry.registerListenerContainer(listener, kafkaListenerContainerFactory, true);
  }

  private KafkaListenerEndpoint createKafkaListenerEndpoint(String topic) {
    MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint =
        createDefaultMethodKafkaListenerEndpoint(topic);
    kafkaListenerEndpoint.setBean(new KafkaTemplateListener(null));
    try {
      kafkaListenerEndpoint.setMethod(KafkaTemplateListener.class.getMethod("onMessage", ConsumerRecord.class));
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("Attempt to call a non-existent method " + e);
    }
    return kafkaListenerEndpoint;
  }

  private KafkaListenerEndpoint createKafkaListenerEndpoint(String topic, BlockingQueue<ConsumerRecord<String, String>> blockingQueue) {
    MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint =
        createDefaultMethodKafkaListenerEndpoint(topic);
    kafkaListenerEndpoint.setBean(new KafkaTemplateListener(blockingQueue));
    try {
      kafkaListenerEndpoint.setMethod(KafkaTemplateListener.class.getMethod("onMessage", ConsumerRecord.class));
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("Attempt to call a non-existent method " + e);
    }
    return kafkaListenerEndpoint;
  }

  private MethodKafkaListenerEndpoint<String, String> createDefaultMethodKafkaListenerEndpoint(String topic) {
    MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint = new MethodKafkaListenerEndpoint<>();
    kafkaListenerEndpoint.setId(topic + generateListenerId());
    kafkaListenerEndpoint.setGroupId(kafkaGroupId);
    kafkaListenerEndpoint.setAutoStartup(true);
    kafkaListenerEndpoint.setTopics(topic);
    kafkaListenerEndpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
    return kafkaListenerEndpoint;
  }

  private String generateListenerId() {
    return kafkaGeneralListenerEndpointId + endpointIdIndex.getAndIncrement();

  }
}
