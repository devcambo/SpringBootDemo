package com.devcambo.api.service;

import com.devcambo.api.constant.AppConstants;
import com.devcambo.api.dto.file.FileUploadResponse;
import com.devcambo.api.exception.FileException;
import com.devcambo.api.exception.ResourceNotFoundException;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {

  private final S3Template s3Template;

  public FileUploadResponse save(@NonNull final MultipartFile file) {
    final var key = generateUniqueFileName(
      Objects.requireNonNull(file.getOriginalFilename())
    );
    try {
      S3Resource upload = s3Template.upload(
        AppConstants.S3_BUCKET_NAME,
        key,
        file.getInputStream()
      );
      return new FileUploadResponse(key);
    } catch (IOException e) {
      throw new FileException(
        e.getMessage() != null ? e.getMessage() : "Failed to upload file to S3"
      );
    }
  }

  public void delete(@NonNull final String objectKey) {
    if (!s3Template.objectExists(AppConstants.S3_BUCKET_NAME, objectKey)) {
      throw new ResourceNotFoundException("File", "objectKey", objectKey);
    }
    s3Template.deleteObject(AppConstants.S3_BUCKET_NAME, objectKey);
  }

  private String generateUniqueFileName(String originalImageName) {
    return String.format(
      "%s%s",
      UUID.randomUUID().toString(),
      originalImageName.substring(originalImageName.lastIndexOf("."))
    );
  }
}
