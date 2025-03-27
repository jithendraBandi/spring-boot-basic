package com.poc.springSecurity.serviceImpl;

import com.poc.springSecurity.exceptions.CustomException;
import com.poc.springSecurity.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
public class S3ServiceImpl implements S3Service {
    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Presigner s3Presigner;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Override
    public List<String> getS3FilesList() {
        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();
        return s3Client.listObjects(listObjectsRequest).contents().stream()
                .map(S3Object::key).toList();
    }

    @Override
    public void uploadFile(MultipartFile file) {
        String originalFileName = Objects.requireNonNullElse(file.getOriginalFilename(), "");
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
        String key = "testing/index".replace(" ", "") + "." + extension;  // make sure no spaces
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
//                .acl(ObjectCannedACL.PUBLIC_READ)  // Grant public read access
                .build();

        try {
            RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
            s3Client.putObject(putObjectRequest, requestBody);  // return PutObjectResponse
//             return getS3ObjectUrl(key); // For getting url of uploaded file

        } catch (IOException e) {
            throw new CustomException("Error occurred during file upload");
        }
    }

    @Override
    public void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        try {
            s3Client.deleteObject(deleteObjectRequest);
        }
        catch (Exception e) {
            throw new CustomException("Error occurred while deleting file with key " + key);
        }
    }

    // Still Learn what is PresignedUrl
    @Override
    public String generatePresignedUrl(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(p -> p
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofHours(1)));

        try {
            URI uri = presignedRequest.url().toURI();
            return uri.toString();
        } catch (URISyntaxException e) {
            throw new CustomException("Error converting URL");
        }
    }

    public String getS3ObjectUrl(String key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
    }
}
