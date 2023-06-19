package com.ersms.app.controller;

import com.ersms.app.model.request.ImageRequest;
import com.ersms.app.model.request.ImageMetadataRequest;
import com.ersms.app.model.response.ImageDto;
import com.ersms.app.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    //Read all images
    @GetMapping
    public List<ImageDto> getAllImages() {
        return imageService.getAllImages();
    }

    //Read one image
    @GetMapping("/{imageId}")
    public ImageDto getImage(@PathVariable Long imageId) {
        return imageService.getImage(imageId);
    }

    //Create an image
    @PostMapping
    public void createImage(@RequestBody ImageRequest request) {
        imageService.createImage(request);
    }

    //Create image's metadata
    @PostMapping("/{imageId}")
    public void createImageMetadata(@PathVariable Long imageId, @RequestBody ImageMetadataRequest request) {
        imageService.createImageMetadata(imageId, request);
    }

    //Delete an image and its metadata
    @DeleteMapping("/{imageId}")
    public void deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
    }

    //Update an image
    @PutMapping("/{imageUrl}")
    public void updateImage(@PathVariable("imageUrl") String imageUrl, @RequestBody ImageRequest request) {
        imageService.updateImage(imageUrl, request);
    }

    //Update image metadata
    @PutMapping("/{imageUrl}/metadata")
    public void updateImage(@PathVariable("imageUrl") String imageUrl, @RequestBody ImageMetadataRequest request) {
        imageService.updateImageMetadata(imageUrl, request);
    }
}
