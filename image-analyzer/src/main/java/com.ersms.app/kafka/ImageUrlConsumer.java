package com.ersms.app.kafka;
import com.ersms.app.service.ImageService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ImageUrlConsumer {

    private final ImageService imageService;

    public ImageUrlConsumer(ImageService imageService) {
        this.imageService = imageService;
    }

    @KafkaListener(topics = "image-url-topic", groupId = "image-analysis-group")
    public void consumeImageUrl(String imageUrl) {
        imageService.processImage(imageUrl);
    }
}
