package com.example.photo_storage_backend.repository;

import com.example.photo_storage_backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface ImageRepository extends JpaRepository<Image, Long> {

    @Transactional
    void deleteByPublicId(String publicId);

}
