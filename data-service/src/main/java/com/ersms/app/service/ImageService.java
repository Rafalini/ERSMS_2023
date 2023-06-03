package com.ersms.app.service;

import com.ersms.app.domain.ImageEntity;
import com.ersms.app.domain.ImageMetadataEntity;
import com.ersms.app.exception.RuntimeExceptionWithHttpStatus;
import com.ersms.app.model.request.ImageRequest;
import com.ersms.app.model.request.ImageMetadataRequest;
import com.ersms.app.model.response.ImageDto;
import com.ersms.app.repository.ImageMetadataRepository;
import com.ersms.app.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public void createImageMetadata(String imageUrl, ImageMetadataRequest request) {
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

    public void updateImage(String imageUrl, ImageRequest request) {

        var image = imageRepository.findByUrl(imageUrl)
                .orElseThrow(() -> new RuntimeExceptionWithHttpStatus("Image at given address cannot be found", HttpStatus.NOT_FOUND));

        if (Objects.nonNull(request.getName()) && !"".equalsIgnoreCase(request.getName())) {
            image.setName(request.getName());
        }
        if (Objects.nonNull(request.getDescription()) && !"".equalsIgnoreCase(request.getDescription())) {
            image.setDescription(request.getDescription());
        }

        imageRepository.save(image);
    }

    public void updateImageMetadata(String imageUrl, ImageMetadataRequest request) {

        var image = imageRepository.findByUrl(imageUrl)
                .orElseThrow(() -> new RuntimeExceptionWithHttpStatus("Image at given address cannot be found", HttpStatus.NOT_FOUND));

        var imageMetadata = imageMetadataRepository.findByImage(image)
                .orElseThrow(() -> new RuntimeExceptionWithHttpStatus("Metadata of the image cannot be found", HttpStatus.NOT_FOUND));

        if (Objects.nonNull(request.getCameraModel()) && !"".equalsIgnoreCase(request.getCameraModel())) {
            imageMetadata.setCameraModel(request.getCameraModel());
        }
        if (Objects.nonNull(request.getAperture()) && !"".equalsIgnoreCase(String.valueOf(request.getAperture()))) {
            imageMetadata.setAperture(request.getAperture());
        }
        if (Objects.nonNull(request.getPixelsVertically()) && !"".equalsIgnoreCase(String.valueOf(request.getPixelsVertically()))) {
            imageMetadata.setPixelsVertically(request.getPixelsVertically());
        }
        if (Objects.nonNull(request.getPixelsHorizontally()) && !"".equalsIgnoreCase(String.valueOf(request.getPixelsHorizontally()))) {
            imageMetadata.setPixelsHorizontally(request.getPixelsHorizontally());
        }
        if (Objects.nonNull(request.getFocalLength()) && !"".equalsIgnoreCase(String.valueOf(request.getFocalLength()))) {
            imageMetadata.setFocalLength(request.getFocalLength());
        }
        if (Objects.nonNull(request.getShutterSpeed()) && !"".equalsIgnoreCase(String.valueOf(request.getShutterSpeed()))) {
            imageMetadata.setShutterSpeed(request.getShutterSpeed());
        }
        if (Objects.nonNull(request.getLocation()) && !"".equalsIgnoreCase(request.getLocation())) {
            imageMetadata.setLocation(request.getLocation());
        }
        if (Objects.nonNull(request.getDateTime()) && !"".equalsIgnoreCase(String.valueOf(request.getDateTime()))) {
            imageMetadata.setDateTime(request.getDateTime());
        }

        imageMetadataRepository.save(imageMetadata);
    }

}
