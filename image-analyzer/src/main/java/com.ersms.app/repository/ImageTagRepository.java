package com.ersms.app.repository;

import com.ersms.app.domain.ImageTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageTagRepository extends JpaRepository<ImageTagEntity, Long> {
    ImageTagEntity findByName(String name);
}
