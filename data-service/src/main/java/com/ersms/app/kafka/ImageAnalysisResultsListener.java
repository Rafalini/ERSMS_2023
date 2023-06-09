package com.ersms.app.kafka;

import com.ersms.app.domain.ImageEntity;
import com.ersms.app.domain.ImageTagEntity;
import com.ersms.app.repository.ImageRepository;
import com.ersms.app.repository.ImageTagRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ImageAnalysisResultsListener {

    private final ImageRepository imageRepository;
    private final ImageTagRepository imageTagRepository;

    public ImageAnalysisResultsListener(ImageRepository imageRepository, ImageTagRepository imageTagRepository) {
        this.imageRepository = imageRepository;
        this.imageTagRepository = imageTagRepository;
    }

    @KafkaListener(topics = "image-analysis-results", groupId = "data-service-group")
    public void consumeImageAnalysisResults(String image) throws JsonProcessingException {
        System.out.println(image);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(image,
                new TypeReference<>() {
                });

        List<ImageTagEntity> tags = new ArrayList<>();
        String str = map.get("tags").toString();
        str = str.substring(1, str.length() - 1); // Removes the square brackets
        String[] tagNames = str.split(", "); // Splits the string on comma followed by space

        for (String tagName : tagNames) {
            ImageTagEntity tag = imageTagRepository.findByName(tagName);
            if (tag == null) {
                tag = ImageTagEntity.builder().name(tagName).build();
            }
            tags.add(tag);
        }

        Long id = Integer.valueOf(map.get("id").toString()).longValue();
        Optional<ImageEntity> optionalImageEntity = imageRepository.findById(id);
        if (optionalImageEntity.isPresent()) {
            ImageEntity imageEntity = optionalImageEntity.get();
            imageEntity.setTags(tags);
            imageRepository.save(imageEntity);
        }
//        var image = imageRepository.findByUrl(imageUrl)
//                .orElseThrow(() -> new RuntimeException("Image at given address cannot be found"));
//
//        List<String> imageTags = new ArrayList<>(Arrays.asList(tagsString.split(", ")));
//
//        for (String tag : imageTags) {
//            ImageTagEntity imageTagEntity = imageTagRepository.findByName(tag);
//
//            if (imageTagEntity == null) {
//                imageTagEntity = new ImageTagEntity();
//                imageTagEntity.setName(tag);
//                imageTagRepository.save(imageTagEntity);
//            }
//
//            // Associate the tag with the image
//            image.getTags().add(imageTagEntity);
//        }
    }
}
