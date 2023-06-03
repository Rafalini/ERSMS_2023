package com.ersms.app.controller;

import com.ersms.app.model.request.ImageRequest;
import com.ersms.app.model.request.MetadataRequest;
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
    @GetMapping("/{imageUrl}")
    public ImageDto getImage(@PathVariable String imageUrl) {
        return imageService.getImage(imageUrl);
    }

    //Create an image
    @PostMapping
    public void createImage(@RequestBody ImageRequest request) {
        imageService.createImage(request);
    }

    //Create image's metadata
    @PostMapping("/{imageUrl}")
    public void createMetadata(@PathVariable String imageUrl, @RequestBody MetadataRequest request) {
        imageService.createMetadata(imageUrl, request);
    }

}
