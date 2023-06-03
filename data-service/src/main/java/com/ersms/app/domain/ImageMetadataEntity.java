package com.ersms.app.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image_metadata")
public class ImageMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cameraModel;

    private BigDecimal aperture;

    private Integer pixelsVertically;

    private Integer pixelsHorizontally;

    private BigDecimal focalLength;

    private Integer shutterSpeed;

    private String location;

    private LocalDateTime dateTime;

    @OneToOne(mappedBy = "metadata")
    private ImageEntity image;

    @JsonBackReference
    public ImageEntity getImage(){
        return image;
    }
}

