package com.ersms.app.kafka;
import com.ersms.app.service.ImageAnalysisService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ImageUrlListener {

    private final ImageAnalysisService imageAnalysisService;

    public ImageUrlListener(ImageAnalysisService imageAnalysisService) {
        this.imageAnalysisService = imageAnalysisService;
    }

    @KafkaListener(topics = "image-url", groupId = "image-analysis-group")
    public void consumeImage(String imageInfo) {
        System.out.println("Image info: " + imageInfo);
        imageAnalysisService.analyzeImage(imageInfo);
    }
}
