package com.ersms.app.repository;

import com.ersms.app.domain.ImageEntity;
import com.ersms.app.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    Optional<ImageEntity> findByUrl(String imageUrl);

    Optional<ImageEntity> findById(Long imageId);

    List<ImageEntity> findAllByUser(UserEntity user);
}
