package com.hab.blog.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka(partitions = 1, topics = { "yourTopic" })
@SpringBootTest
public class KafkaIntegrationTest {

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @TestModel
//    public void testKafkaEvent() {
//        kafkaTemplate.send("yourTopic", "test message");
//        // 进行断言或验证
//    }
}
