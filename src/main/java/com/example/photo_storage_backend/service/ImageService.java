package com.example.photo_storage_backend.service;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.example.photo_storage_backend.model.Image;
import com.example.photo_storage_backend.repository.ImageRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class ImageService {

    private Cloudinary cloudinary;
    private ImageRepository imageRepository;

    @Value("${cloudNameValue}")
    private String cloudNameValue;

    @Value("${apiKeyValue}")
    private String apiKeyValue;

    @Value("${apiSecretValue}")
    private String apiSecretValue;

    @Autowired
    public ImageService(ImageRepository imageRepository,
                        @Value("${cloudNameValue}") String cloudNameValue,
                        @Value("${apiKeyValue}") String apiKeyValue,
                        @Value("${apiSecretValue}") String apiSecretValue) {
        this.imageRepository = imageRepository;
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudNameValue,
                "api_key", apiKeyValue,
                "api_secret", apiSecretValue));
    }


    public String uploadImageAndSaveToDb(String path) {
        File file = new File(path);
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            imageRepository.save(new Image(
                    uploadResult.get("url").toString(),
                    uploadResult.get("public_id").toString()));
        } catch (IOException e) {
            //todo
        }
        return uploadResult.get("url").toString();
    }


    public String uploadFromForm(InputStream inputStream, String fileName) {

        File file = new File("temp/"+fileName);

        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            imageRepository.save(new Image(
                    uploadResult.get("url").toString(),
                    uploadResult.get("public_id").toString()));

        } catch (IOException e) {
            //todo
        } finally {
            file.delete();
        }
        return uploadResult.get("url").toString();
    }


    public void deleteImageAndDeleteFromDb(String publicId) {
        Map deleteResult = null;
        try {
            deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            imageRepository.deleteByPublicId(publicId);
        } catch (IOException e) {
            //todo
        }
    }
}
