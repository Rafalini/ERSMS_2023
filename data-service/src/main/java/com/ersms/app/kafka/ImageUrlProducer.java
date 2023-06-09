package com.ersms.app.kafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ImageUrlProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ImageUrlProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void produceUrl(String imageUrl) {
        kafkaTemplate.send("image-url", imageUrl);
    }
}
