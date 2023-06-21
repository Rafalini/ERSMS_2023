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

    @Query(value = "SELECT DISTINCT i.*\n" +
            "FROM image i\n" +
            "JOIN image_recognition ir ON i.id = ir.image_id\n" +
            "JOIN image_tag it ON ir.image_tag_id = it.id\n" +
            "WHERE LOWER(it.name) IN (?1)", nativeQuery = true)
    List<ImageEntity> findAllByTags(List<String> tags);
}

