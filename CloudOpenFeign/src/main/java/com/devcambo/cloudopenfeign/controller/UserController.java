package com.devcambo.cloudopenfeign.controller;

import com.devcambo.cloudopenfeign.client.FakeAPIClient;
import com.devcambo.cloudopenfeign.dto.user.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final FakeAPIClient userClient;

  @GetMapping
  public ResponseEntity<List<UserDto>> findAllUsers() {
    log.info("Fetching all users");
    return ResponseEntity.ok(userClient.findAll());
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> findUserById(@PathVariable Long userId) {
    log.info("Fetching user with id {}", userId);
    return ResponseEntity.ok(userClient.findById(userId));
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(@RequestBody UserRequestDto userRequestDto) {
    log.info("Creating user {}", userRequestDto);
    return ResponseEntity.ok(userClient.create(userRequestDto));
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserDto> updateUser(
    @PathVariable Long userId,
    @RequestBody UserUpdateDto userUpdateDto
  ) {
    log.info("Updating user with id {}", userId);
    return ResponseEntity.ok(userClient.update(userId, userUpdateDto));
  }

  @PostMapping("/is-available")
  public ResponseEntity<CheckEmailResponseDto> isEmailAvailable(
    @RequestBody CheckEmailRequestDto checkEmailRequestDto
  ) {
    log.info("Checking if email is available {}", checkEmailRequestDto);
    return ResponseEntity.ok(userClient.isEmailAvailable(checkEmailRequestDto));
  }
}
