package com.devcambo.cloudopenfeign.client;

import com.devcambo.cloudopenfeign.dto.file.FileDto;
import com.devcambo.cloudopenfeign.dto.user.*;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "${fake-api.name}", url = "${fake-api.url}")
public interface FakeAPIClient {
  @RequestMapping(method = RequestMethod.GET, value = "/users")
  List<UserDto> findAll();

  @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
  UserDto findById(@PathVariable Long id);

  @RequestMapping(method = RequestMethod.POST, value = "/users")
  UserDto create(@RequestBody UserRequestDto userRequestDto);

  @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
  UserDto update(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto);

  @RequestMapping(method = RequestMethod.POST, value = "/users/is-available")
  CheckEmailResponseDto isEmailAvailable(
    @RequestBody CheckEmailRequestDto checkEmailRequestDto
  );

  @PostMapping(value = "/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  FileDto uploadFile(@RequestPart("file") MultipartFile file);
}
