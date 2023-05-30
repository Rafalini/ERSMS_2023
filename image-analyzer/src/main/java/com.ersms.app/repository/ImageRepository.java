package com.ersms.app.repository;

import com.ersms.app.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    ImageEntity findByImageUrl(String imageUrl);
}
