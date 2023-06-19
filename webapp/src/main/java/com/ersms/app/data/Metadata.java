package com.ersms.app.data;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class Metadata {
    private Long id;
    private String cameraModel;
    private BigDecimal aperture;
    private Integer pixelsVertically;
    private Integer pixelsHorizontally;
    private BigDecimal focalLength;
    private Integer shutterSpeed;
    private String location;
    private LocalDateTime dateTime;
}
