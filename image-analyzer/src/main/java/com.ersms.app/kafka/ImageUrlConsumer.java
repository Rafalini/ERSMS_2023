//package com.ersms.app.kafka;
//import com.ersms.app.service.ImageAnalysisService;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ImageUrlConsumer {
//
//    private final ImageAnalysisService imageAnalysisService;
//
//    public ImageUrlConsumer(ImageAnalysisService imageAnalysisService) {
//        this.imageAnalysisService = imageAnalysisService;
//    }
//
//    @KafkaListener(topics = "image-url", groupId = "image-analysis-group")
//    public void consumeImageUrl(String imageUrl) {
//        imageAnalysisService.analyzeImage(imageUrl);
//    }
//}
