package com.jisu;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class AvroProducerBackWard2 {
    public static void main(String[] args) {
        // Avro 스키마 정의
        String schemaString = "{"
                + "\"namespace\": \"studentbackward.avro\","
                + "\"type\": \"record\","
                + "\"doc\": \"This is an example of Avro.\","
                + "\"name\": \"StudentBackward\","
                + "\"fields\": ["
                + "{\"name\": \"name\", \"type\": [\"null\", \"string\"], \"default\": null, \"doc\": \"Name of the student\"},"
                + "{\"name\": \"class\", \"type\": \"int\", \"default\": 1, \"doc\": \"Class of the student\"},"
                + "{\"name\": \"age\", \"type\": \"int\", \"default\": 1, \"doc\": \"Age of the student\"}"
                + "]"
                + "}";

        Schema schema = new Schema.Parser().parse(schemaString);

        // Kafka producer properties 설정
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        // KafkaProducer 생성
        KafkaProducer<String, GenericRecord> producer = new KafkaProducer<>(props);

        // Avro 레코드 생성
        GenericRecord student = new GenericData.Record(schema);
        student.put("name", "Peter");
        student.put("class", 1);
        student.put("age", 20);

        // ProducerRecord 생성
        ProducerRecord<String, GenericRecord> record = new ProducerRecord<>("peter-avro3", student);

        // 메시지 전송 및 결과 확인
        try {
            RecordMetadata metadata = producer.send(record).get();
            System.out.printf("Message sent to topic:%s partition:%s  offset:%s%n",
                    metadata.topic(), metadata.partition(), metadata.offset());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Producer 닫기
        producer.close();
    }
}