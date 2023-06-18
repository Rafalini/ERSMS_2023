package com.ersms.app.repository;

import com.ersms.app.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    Optional<ImageEntity> findByUrl(String imageUrl);

    @Query(value = "SELECT * FROM image WHERE user_email = :userEmail", nativeQuery = true)
    List<ImageEntity> findAllByUserEmail(String userEmail);
}
