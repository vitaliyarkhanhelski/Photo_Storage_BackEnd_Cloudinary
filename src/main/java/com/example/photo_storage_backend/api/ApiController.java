package com.example.photo_storage_backend.api;

import com.example.photo_storage_backend.model.Image;
import com.example.photo_storage_backend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

private ImageRepository imageRepository;

    @Autowired
    public ApiController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/api")
    List<Image> showAll(){
        return imageRepository.findAll();
    }
}
