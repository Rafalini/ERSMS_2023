package com.ersms.app.service;

import com.ersms.app.model.response.ImageDto;
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

    public List<ImageDto> getAllImages() {
        return imageRepository.findAll()
                .stream()
                .map(ImageDto::from)
                .collect(Collectors.toList());
    }

    public ImageDto getImage(String url) {
        return ImageDto.from(imageRepository.findByUrl(url));
    }
}
