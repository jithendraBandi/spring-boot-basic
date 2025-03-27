package com.poc.springSecurity.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {
    List<String> getS3FilesList();

    void uploadFile(MultipartFile file);

    void deleteFile(String key);

    String generatePresignedUrl(String key);
}
