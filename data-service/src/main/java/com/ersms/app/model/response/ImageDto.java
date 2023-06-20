package com.ersms.app.model.response;

import com.ersms.app.domain.ImageEntity;
import com.ersms.app.domain.ImageMetadataEntity;
import com.ersms.app.domain.ImageTagEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDto {
    private Long id;
    private String url;
    private String name;
    private String description;
    private ImageMetadataEntity metadata;
    private List<ImageTagEntity> tags;
    private String userEmail;

    public static ImageDto from(ImageEntity entity){
        return ImageDto.builder()
                .id(entity.getId())
                .url(entity.getUrl())
                .name(entity.getName())
                .description(entity.getDescription())
                .metadata(entity.getMetadata())
                .tags(entity.getTags())
                .userEmail(entity.getUser().getEmail())
                .build();
    }
}
