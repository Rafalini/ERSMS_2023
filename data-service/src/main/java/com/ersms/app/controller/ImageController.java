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

    //Read images with given tags
    @GetMapping("/tags")
    public List<ImageDto> getImagesWithTags(@RequestParam(value="tags") List<String> tags)
    {
        return imageService.getImagesWithTags(tags);
    }

    //Create an image
    @PostMapping
    public void createImage(@RequestBody ImageRequest request) {
        imageService.createImage(request);
    }

    //Create image's metadata
    @PostMapping("/{imageId}/metadata")
    public void createImageMetadata(@PathVariable Long imageId, @RequestBody ImageMetadataRequest request) {
        imageService.createImageMetadata(imageId, request);
    }

    //Delete an image and its metadata
    @DeleteMapping("/{imageId}")
    public void deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
    }

    //Update an image
    @PutMapping("/{imageId}")
    public void updateImage(@PathVariable("imageId") Long imageId, @RequestBody ImageRequest request) {
        imageService.updateImage(imageId, request);
    }

    //Update image metadata
    @PutMapping("/{imageId}/metadata")
    public void updateImage(@PathVariable("imageId") Long imageId, @RequestBody ImageMetadataRequest request) {
        imageService.updateImageMetadata(imageId, request);
    }
}
