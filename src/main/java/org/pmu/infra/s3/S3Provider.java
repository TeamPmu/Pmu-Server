package org.pmu.infra.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pmu.global.error.ErrorCode;
import org.pmu.global.error.InvalidValueException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Provider {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFileToS3Bucket(MultipartFile multipartFile) {
        validateMultipartFile(multipartFile);
        ObjectMetadata objectMetadata = getObjectMetadata(multipartFile);
        String fileName = getFileName(multipartFile);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(bucket, fileName, inputStream, objectMetadata);
        } catch (IOException e) {
            log.error("Failed to upload image ", e);
            throw new InvalidValueException(ErrorCode.INVALID_FILE_UPLOAD);
        }
        return amazonS3Client.getResourceUrl(bucket, fileName);
    }

    private void validateMultipartFile(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new InvalidValueException(ErrorCode.INVALID_MULTIPART_FILE);
        }
    }

    private ObjectMetadata getObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        return objectMetadata;
    }

    private String getFileName(MultipartFile multipartFile) {
        return String.format("%s/%s-%s", "image", UUID.randomUUID(), multipartFile.getOriginalFilename());
    }
}
