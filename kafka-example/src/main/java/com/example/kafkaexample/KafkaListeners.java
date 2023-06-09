package com.example.kafkaexample;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(topics = "image-analysis-results", groupId = "foo")
    void listener(String data) {
        System.out.println("listener received: " + data);
//        log.info("Listener received: " + data + " 🎉");
    }
}
