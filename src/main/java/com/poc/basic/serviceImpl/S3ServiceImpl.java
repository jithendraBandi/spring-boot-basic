package com.poc.basic.serviceImpl;

import com.poc.basic.exceptions.CustomException;
import com.poc.basic.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.poc.basic.utils.Constants.MAX_SIZE_2MB;

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
    public String uploadFile(MultipartFile file, String fileName) {
        MultipartFile compressedFile = file;
        if (file.getSize() > MAX_SIZE_2MB) {
            try {
                if (Objects.requireNonNull(file.getContentType()).startsWith("image")) {
                    compressedFile = compressImage(file);
//                } else if (file.getContentType().equals("application/pdf")) {
//                    return compressPdf(file);
                } else {
                    // Other file types, handle accordingly (e.g., ZIP or other strategies)
                    throw new UnsupportedOperationException("File format not supported for compression");
                }

            } catch (IOException e) {
                throw new CustomException("File is too large to compress to required size");
            }
        }
        String originalFileName = Objects.requireNonNullElse(compressedFile.getOriginalFilename(), "");
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
        String key = fileName + "." + extension;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
//                .acl(ObjectCannedACL.PUBLIC_READ)  // Grant public read access
                .build();

        try {
            RequestBody requestBody = RequestBody.fromInputStream(compressedFile.getInputStream(), compressedFile.getSize());
            s3Client.putObject(putObjectRequest, requestBody);  // return PutObjectResponse
             return getS3ObjectUrl(key); // For getting url of uploaded file

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

    private MultipartFile compressImage(MultipartFile file) throws IOException {
        // Read the image from the file
        BufferedImage image = ImageIO.read(file.getInputStream());

        // Initial image quality and size check
        float quality = 0.99f; // Maximum quality (1.0 means no compression)
        int maxFileSize = 2 * 1024 * 1024; // 2MB in bytes

        // Loop to compress the image and check the file size
        do {
            // Create an image writer to compress the image
            try (ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream()) {
                // Get an ImageWriter for JPEG format
                Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
                if (writers.hasNext()) {
                    ImageWriter writer = writers.next();
                    ImageWriteParam param = writer.getDefaultWriteParam();

                    // Set compression mode and quality
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(quality); // Set the quality level

                    // Write the image with the specified compression quality
                    writer.setOutput(ImageIO.createImageOutputStream(compressedImageStream));
                    writer.write(null, new IIOImage(image, null, null), param);

                    // Check the file size
                    if (compressedImageStream.size() <= maxFileSize) {
                        byte[] compressedFileBytes = compressedImageStream.toByteArray(); // Return when the file size is within limits
                        return new MockMultipartFile(
                                Objects.requireNonNull(file.getOriginalFilename()),
                                file.getOriginalFilename(),
                                file.getContentType(),
                                compressedFileBytes
                        );
                    }
                }
            } catch (IOException e) {
                throw new IOException("Error during image compression: " + e.getMessage(), e);
            }
            quality -= 0.05f; // Reduce quality by 10% after each iteration

        } while (quality > 0.01f); // Stop if quality is too low (e.g., below 0.1)

        // If we reach here, we couldn't compress the image enough to fit within 2MB
        throw new IOException("Unable to compress image to fit within 2MB.");
    }
}
