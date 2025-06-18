package com.devcambo.api.controller;

import com.devcambo.api.dto.user.UserRequestDto;
import com.devcambo.api.dto.user.UserResponseDto;
import com.devcambo.api.dto.user.UserUpdateDto;
import com.devcambo.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<Page<UserResponseDto>> findAllUsers(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "5") int size,
    @RequestParam(defaultValue = "userId") String sortBy,
    @RequestParam(defaultValue = "true") boolean ascending
  ) {
    log.info("Fetching all users");
    Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);
    return ResponseEntity.ok(userService.findAll(pageable));
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long userId) {
    log.info("Fetching user with id: {}", userId);
    return ResponseEntity.ok(userService.findById(userId));
  }

  @PostMapping
  public ResponseEntity<Void> createUser(
    @Valid @RequestBody UserRequestDto userRequestDto
  ) {
    log.info("Creating user: {}", userRequestDto.email());
    userService.create(userRequestDto);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{userId}")
  public ResponseEntity<Void> updateUser(
    @PathVariable Long userId,
    @Valid @RequestBody UserUpdateDto userUpdateDto
  ) {
    log.info("Updating user with id: {}", userId);
    userService.update(userId, userUpdateDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    log.info("Deleting user with id: {}", userId);
    userService.delete(userId);
    return ResponseEntity.noContent().build();
  }
}
