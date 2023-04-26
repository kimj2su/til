package org.example.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.event.EventHandler;
import org.example.event.FileEventHandler;
import org.example.event.FileEventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;

public class FileAppendProducer {

    public static final Logger logger = LoggerFactory.getLogger(FileAppendProducer.class.getName());
    
    public static void main(String[] args) {

        String topicName = "file-topic";

        //KafkaProducer configuration setting
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //KafkaProducer 객체 생성
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);
        boolean sync = false;
        File file = new File("/Users/kimjisu/Desktop/Intellij/til/kafka/KafkaProj-01/practice/src/main/resources/pizza_append.txt");
        // KafkaProducer 객체 생성 -> ProducerRecords생성 -> send() 비동기 방식 전송
        EventHandler eventHandler = new FileEventHandler(kafkaProducer, topicName, sync);
        FileEventSource fileEventSource = new FileEventSource(1000, file, eventHandler);
        Thread fileEventSourceThread = new Thread(fileEventSource);
        fileEventSourceThread.start();

        try {
            fileEventSourceThread.join();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        kafkaProducer.close();
    }
}
