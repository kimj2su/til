package com.jisu;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import student.avro.Student;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class AvroConsumer2 {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http//localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "avro-consumer-group2");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("specific.avro.reader", "true");

        // KafkaConsumer 생성
        KafkaConsumer<String, Student> consumer = new KafkaConsumer<>(props);

        // 구독할 토픽 설정
        consumer.subscribe(Collections.singletonList("peter-avro2"));

        // 메시지 소비 루프
        try {
            while (true) {
                ConsumerRecords<String, Student> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, Student> record : records) {
                    System.out.printf("Consumed message: topic = %s, partition = %s, offset = %s, key = %s, value = %s%n",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value());
                }
            }
        } finally {
            consumer.close();
        }
    }
}
