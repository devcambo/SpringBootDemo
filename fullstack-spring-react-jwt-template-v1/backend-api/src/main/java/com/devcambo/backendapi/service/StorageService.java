package com.devcambo.backendapi.service;

import com.devcambo.backendapi.dto.file.FileDto;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
  FileDto save(final MultipartFile file);
  void delete(final String objectKey);
}
