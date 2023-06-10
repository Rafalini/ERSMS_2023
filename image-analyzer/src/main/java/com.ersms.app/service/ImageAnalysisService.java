package com.ersms.app.service;

import com.ersms.app.model.ImageAnalyzerResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.google.cloud.vision.v1.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ImageAnalysisService {

    private final KafkaTemplate<String, ImageAnalyzerResponse> kafkaTemplate;

    public ImageAnalysisService(KafkaTemplate<String, ImageAnalyzerResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void analyzeImage(String imageUrl) {
        List<String> imageTags = extractTagsFromImage(imageUrl);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, Object> map = objectMapper.readValue(imageUrl,
                    new TypeReference<>() {
                    });
            ImageAnalyzerResponse imageAnalyzerResponse = ImageAnalyzerResponse.builder().id((int)map.get("id")).tags(imageTags).build();
            // Publish the analyzed tags to another Kafka topic
            kafkaTemplate.send("image-analysis-results", imageAnalyzerResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> extractTagsFromImage(String imageUrl) {

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // For this example, let's assume we have a list of hardcoded tags
        return Arrays.asList("tag1", "tag2", "tag3");
    }
}
