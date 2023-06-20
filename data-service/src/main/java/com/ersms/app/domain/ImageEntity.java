package com.ersms.app.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String url;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "metadata_id")
    private ImageMetadataEntity metadata;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ImageRecognition",
            inverseJoinColumns = {@JoinColumn(name = "image_tag_id")},
            joinColumns = {@JoinColumn(name = "image_id")})

    private List<ImageTagEntity> tags;

    @JsonManagedReference
    public ImageMetadataEntity getMetadata() {
        return metadata;
    }
}

