package com.ersms.app.service;

import com.ersms.app.domain.ImageEntity;
import com.ersms.app.domain.ImageMetadataEntity;
import com.ersms.app.exception.RuntimeExceptionWithHttpStatus;
import com.ersms.app.model.request.ImageRequest;
import com.ersms.app.model.request.MetadataRequest;
import com.ersms.app.model.response.ImageDto;
import com.ersms.app.repository.ImageMetadataRepository;
import com.ersms.app.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageMetadataRepository imageMetadataRepository;

    public List<ImageDto> getAllImages() {
        return imageRepository.findAll()
                .stream()
                .map(ImageDto::from)
                .collect(Collectors.toList());
    }

    public ImageDto getImage(String imageUrl) {
        var image = imageRepository.findByUrl(imageUrl)
                .orElseThrow(() -> new RuntimeExceptionWithHttpStatus("Image at given address cannot be found", HttpStatus.NOT_FOUND));

        return ImageDto.from(image);
    }

    public void createImage(ImageRequest request) {

        if (imageRepository.findByUrl(request.getUrl()).isPresent()) {
            throw new RuntimeExceptionWithHttpStatus("The image already exists in the database", HttpStatus.CONFLICT);
        }

        var image = ImageEntity.builder()
                .url(request.getUrl())
                .name(request.getName())
                .description(request.getDescription())
                .build();
        imageRepository.save(image);
    }

    public void createMetadata(String imageUrl, MetadataRequest request) {
        var image = imageRepository.findByUrl(imageUrl)
                .orElseThrow(() -> new RuntimeExceptionWithHttpStatus("Image at given address cannot be found", HttpStatus.NOT_FOUND));

        var imageMetadata = ImageMetadataEntity.builder()
                .cameraModel(request.getCameraModel())
                .aperture(request.getAperture())
                .pixelsVertically(request.getPixelsVertically())
                .pixelsHorizontally(request.getPixelsHorizontally())
                .focalLength(request.getFocalLength())
                .shutterSpeed(request.getShutterSpeed())
                .location(request.getLocation())
                .dateTime(request.getDateTime())
                .image(image)
                .build();

        imageMetadataRepository.save(imageMetadata);
    }

    public void deleteImage(String imageUrl) {
        var image = imageRepository.findByUrl(imageUrl)
                .orElseThrow(() -> new RuntimeExceptionWithHttpStatus("Image at given address cannot be found", HttpStatus.NOT_FOUND));
        var imageMetadata = imageMetadataRepository.findByImage(image);
        imageMetadata.ifPresent(imageMetadataRepository::delete);

        imageRepository.delete(image);
    }

}
