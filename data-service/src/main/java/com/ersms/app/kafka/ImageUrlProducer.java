//package com.ersms.app.kafka;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import java.util.List;
//
//public class ImageUrlProducer {
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    public ImageUrlProducer(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//    public void produceUrl(String imageUrl) {
//        String imgUrl = "img";
//        kafkaTemplate.send("image-url", imgUrl);
//    }
//}
