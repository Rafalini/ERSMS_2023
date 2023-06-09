package com.ersms.app.kafka;
import com.ersms.app.domain.ImageEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ImageUrlProducer {
    private final KafkaTemplate<String, ImageEntity> kafkaTemplate;

    public ImageUrlProducer(KafkaTemplate<String, ImageEntity> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceImage(ImageEntity image) {
        kafkaTemplate.send("image-url", image);
    }
}
