package com.ersms.app.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageMetadataRequest {
    private String cameraModel;
    private BigDecimal aperture;
    private Integer pixelsVertically;
    private Integer pixelsHorizontally;
    private BigDecimal focalLength;
    private Integer shutterSpeed;
    private String location;
    private LocalDateTime dateTime;
}
