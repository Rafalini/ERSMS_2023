package com.ersms.app.repository;

import com.ersms.app.domain.ImageEntity;
import com.ersms.app.domain.ImageMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageMetadataRepository extends JpaRepository<ImageMetadataEntity, Long> {

    Optional<ImageMetadataEntity> findByImage(ImageEntity image);
}