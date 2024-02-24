package com.example.demo.kafka;

import com.example.demo.TestMongoDBConfiguration;
import com.example.demo.kafka.avro.PaymentMethod;
import com.example.demo.kafka.avro.Recipient;
import com.example.demo.kafka.avro.Sender;
import com.example.demo.kafka.avro.Shipment;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestMongoDBConfiguration.class)
public class KafkaTest extends KafkaTestSupport {
    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

    @Value("${test.topic}")
    private String topic;

    @Autowired
    private TestRepository testRepository;

    @BeforeEach
    void setUp() {
        TestCollection jisu = new TestCollection(null, "jisu");
        testRepository.save(jisu);
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived()
            throws Exception {
        Shipment shipment = Shipment.newBuilder()
            .setSender(new Sender("John Doe", "123 Main St", "123", "123-456-7890"))
            .setRecipient(new Recipient("Jane Doe", "456 Elm St", "456", "456-789-0123"))
            .setContents(Arrays.asList("Item1", "Item2"))
            .setCharge(100L)
            .setPaymentMethod(PaymentMethod.Cash)
            .build();
        producer.send(topic, shipment);

        TimeUnit.SECONDS.sleep(1);

        assertThat(testRepository.findAll()).isEmpty();
    }
}
