package com.ersms.app.service;

import com.ersms.app.domain.ImageEntity;
import com.ersms.app.domain.ImageTagEntity;
import com.ersms.app.repository.ImageRepository;
import com.ersms.app.repository.ImageTagRepository;
import com.google.cloud.vision.v1.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageTagRepository imageTagRepository;

    public ImageService(ImageRepository imageRepository, ImageTagRepository imageTagRepository) {
        this.imageRepository = imageRepository;
        this.imageTagRepository = imageTagRepository;
    }

    @Transactional
    public void processImage(String imageUrl) {
        ImageEntity imageEntity = imageRepository.findByImageUrl(imageUrl);

        if (imageEntity == null) {
            imageEntity = new ImageEntity();
            imageEntity.setUrl(imageUrl);
            imageRepository.save(imageEntity);
        }

        // Perform image analysis using Google Cloud Vision API
        List<String> imageTags = extractTagsFromImage(imageUrl);

        for (String tag : imageTags) {
            ImageTagEntity imageTagEntity = imageTagRepository.findByName(tag);

            if (imageTagEntity == null) {
                imageTagEntity = new ImageTagEntity();
                imageTagEntity.setName(tag);
                imageTagRepository.save(imageTagEntity);
            }

            // Associate the tag with the image
            imageEntity.getTags().add(imageTagEntity);
        }
    }

    private List<String> extractTagsFromImage(String imageUrl) {
        // Code to use Google Cloud Vision API and extract image labels/tags
        // Replace with your own implementation using the Google Cloud Vision API client library

        // For this example, let's assume we have a list of hardcoded tags
        return List.of("tag1", "tag2", "tag3");
    }
}

