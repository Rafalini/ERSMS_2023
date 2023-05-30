package com.ersms.app.service;

import com.ersms.app.domain.ImageEntity;
import com.ersms.app.domain.ImageTagEntity;
import com.ersms.app.repository.ImageRepository;
import com.ersms.app.repository.ImageTagRepository;
import com.google.cloud.vision.v1.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableJpaRepositories("com.ersms.app.repository")
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

        List<String> imageTags = new ArrayList<>();

        try {
            imageTags = extractTagsFromImage(imageUrl);
        } catch(IOException e) {
            e.printStackTrace();
        }

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

    private List<String> extractTagsFromImage(String imageUrl) throws IOException {

        List<String> tags = new ArrayList<>();

        // Create an Image instance from the URL
        ImageSource imageSource = ImageSource.newBuilder().setImageUri(imageUrl).build();
        Image image = Image.newBuilder().setSource(imageSource).build();

        // Create a feature request for label detection
        Feature feature = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image).build();

        // Authenticate and create an ImageAnnotatorClient
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            // Send the request and receive the response
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(List.of(request));

            // Process the response
            for (AnnotateImageResponse annotateResponse : response.getResponsesList()) {
                if (annotateResponse.hasError()) {
                    System.err.println("Error: " + annotateResponse.getError().getMessage());
                    return tags;
                }

                // Extract and add the tags to the list
                for (EntityAnnotation annotation : annotateResponse.getLabelAnnotationsList()) {
                    tags.add(annotation.getDescription());
                }
            }
        }

        return tags;
    }
}

