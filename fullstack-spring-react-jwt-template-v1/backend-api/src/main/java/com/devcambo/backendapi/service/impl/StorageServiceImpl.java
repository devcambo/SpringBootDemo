package com.devcambo.backendapi.service.impl;

import com.devcambo.backendapi.dto.file.FileDto;
import com.devcambo.backendapi.exception.InvalidFileTypeException;
import com.devcambo.backendapi.exception.ResourceNotFoundException;
import com.devcambo.backendapi.service.StorageService;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

  private final S3Template s3Template;

  @Value("${app.s3.bucket-name}")
  private String bucketName;

  @Override
  public FileDto save(final MultipartFile file) {
    if (!isSupportedContentType(Objects.requireNonNull(file.getContentType()))) {
      throw new InvalidFileTypeException("Only PNG or JPG or JPEG images are allowed");
    }
    final var key = generateUniqueFileName(
      Objects.requireNonNull(file.getOriginalFilename())
    );
    try {
      S3Resource upload = s3Template.upload(
        bucketName,
        key,
        file.getInputStream()
      );
      return new FileDto(upload.getFilename());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(String objectKey) {
    if (!s3Template.objectExists(bucketName, objectKey)) {
      throw new ResourceNotFoundException("File not found!");
    }
    s3Template.deleteObject(bucketName, objectKey);
  }

  private static String generateUniqueFileName(String originalFileName) {
    return String.format(
      "%s%s",
      UUID.randomUUID().toString(),
      originalFileName.substring(originalFileName.lastIndexOf("."))
    );
  }

  // TODO: temporally solution
  private static boolean isSupportedContentType(String contentType) {
    return (
      contentType.equals("image/png") ||
      contentType.equals("image/jpg") ||
      contentType.equals("image/jpeg")
    );
  }
}
