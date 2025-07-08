package com.devcambo.backendapi.controller;

import com.devcambo.backendapi.dto.file.FileDto;
import com.devcambo.backendapi.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Slf4j
public class StorageController {

  private final StorageService storageService;

  @PostMapping("/upload")
  public ResponseEntity<FileDto> uploadFile(
    @RequestPart("file") final MultipartFile file
  ) {
    log.info("Uploading file {}", file.getOriginalFilename());
    return ResponseEntity.ok(storageService.save(file));
  }

  @DeleteMapping("/{fileName}")
  public ResponseEntity<?> deleteFile(@PathVariable String fileName) {
    log.info("Deleting file: {}", fileName);
    storageService.delete(fileName);
    return ResponseEntity.noContent().build();
  }
}
