package com.devcambo.awss3.controller;

import com.devcambo.awss3.service.StorageService;
import io.awspring.cloud.s3.S3Resource;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {

  private final StorageService storageService;

  @PostMapping("/upload")
  public ResponseEntity<?> uploadFile(@RequestPart("file") final MultipartFile file) {
    log.info("Uploading file {}", file.getOriginalFilename());
    storageService.save(file);
    return ResponseEntity.ok().build();
  }

  @GetMapping(value = "/{fileName}", produces = "application/octet-stream")
  public ResponseEntity<S3Resource> downloadFile(@PathVariable String fileName) {
    log.info("Downloading file: {}", fileName);
    return ResponseEntity.ok(storageService.retrieve(fileName));
  }

  @DeleteMapping("/{fileName}")
  public ResponseEntity<?> deleteFile(@PathVariable String fileName) {
    log.info("Deleting file: {}", fileName);
    storageService.delete(fileName);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/url/{fileName}")
  public ResponseEntity<URL> generateViewablePresignedUrl(@PathVariable String fileName) {
    log.info("Generating presigned URL for file: {}", fileName);
    return ResponseEntity.ok(storageService.generateViewablePresignedUrl(fileName));
  }
}
