package com.example.photo_storage_backend;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableVaadin
public class PhotoStorageBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoStorageBackEndApplication.class, args);
    }

}
