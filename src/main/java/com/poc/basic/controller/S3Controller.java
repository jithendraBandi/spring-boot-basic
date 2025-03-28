package com.poc.basic.controller;

import com.poc.basic.dto.response.ApiResponse;
import com.poc.basic.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/s3")
public class S3Controller {
    @Autowired
    private S3Service s3Service;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getS3FilesList() {
        List<String> s3Files = s3Service.getS3FilesList();
        ApiResponse response = new ApiResponse(s3Files, "S3 Files successfully retrieved");
        return ResponseEntity.ok(response);
    }

    // Still learn what this url does
    @GetMapping("/presigned-url")
    public ResponseEntity<ApiResponse> generatePresignedUrl(@RequestParam String key) {
        String s3File = s3Service.generatePresignedUrl(key);
        ApiResponse response = new ApiResponse(s3File, "S3 File successfully retrieved");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadFile(@RequestParam MultipartFile file, @RequestParam String fileName) {
        String url = s3Service.uploadFile(file,fileName); // pass url back if required
        ApiResponse response = new ApiResponse("File uploaded successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteFile(@RequestParam String key) {
        s3Service.deleteFile(key);
        ApiResponse response = new ApiResponse("File deleted successfully for key: " + key);
        return ResponseEntity.ok(response);
    }
}
