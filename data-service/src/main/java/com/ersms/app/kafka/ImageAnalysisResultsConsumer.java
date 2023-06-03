package com.ersms.app.kafka;
import com.ersms.app.domain.ImageEntity;
import com.ersms.app.domain.ImageTagEntity;
import com.ersms.app.repository.ImageRepository;
import com.ersms.app.repository.ImageTagRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ImageAnalysisResultsConsumer {

    private final ImageRepository imageRepository;
    private final ImageTagRepository imageTagRepository;

    public ImageAnalysisResultsConsumer(ImageRepository imageRepository, ImageTagRepository imageTagRepository) {
        this.imageRepository = imageRepository;
        this.imageTagRepository = imageTagRepository;
    }
/*
    @KafkaListener(topics = "image-analysis-results", groupId = "data-service-group")
    public void consumeImageAnalysisResults(String imageUrl, String tagsString) {

        var image = imageRepository.findByUrl(imageUrl)
                .orElseThrow(() -> new RuntimeException("Image at given address cannot be found"));

        List<String> imageTags = new ArrayList<>(Arrays.asList(tagsString.split(", ")));

        for (String tag : imageTags) {
            ImageTagEntity imageTagEntity = imageTagRepository.findByName(tag);

            if (imageTagEntity == null) {
                imageTagEntity = new ImageTagEntity();
                imageTagEntity.setName(tag);
                imageTagRepository.save(imageTagEntity);
            }

            // Associate the tag with the image
            image.getTags().add(imageTagEntity);
        }
    }
 */
}
