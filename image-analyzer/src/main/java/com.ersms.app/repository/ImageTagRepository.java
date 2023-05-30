package com.ersms.app.repository;

import com.ersms.app.domain.ImageTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageTagRepository extends JpaRepository<ImageTagEntity, Long> {
    ImageTagEntity findByName(String name);
}
