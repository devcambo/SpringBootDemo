package com.devcambo.cloudopenfeign.controller;

import com.devcambo.cloudopenfeign.client.FakeAPIClient;
import com.devcambo.cloudopenfeign.dto.file.FileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {

  private final FakeAPIClient fileClient;

  @PostMapping("/upload")
  public ResponseEntity<FileDto> uploadFile(@RequestPart MultipartFile file) {
    log.info("Received file: {}", file.getOriginalFilename());
    FileDto fileDto = fileClient.uploadFile(file);
    return ResponseEntity.ok(fileDto);
  }
}
