package com.devcambo.crudjpa.controller;

import com.devcambo.crudjpa.dto.user.UserDto;
import com.devcambo.crudjpa.dto.user.UserRequestDto;
import com.devcambo.crudjpa.dto.user.UserUpdateDto;
import com.devcambo.crudjpa.service.UserService;
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
  public ResponseEntity<Page<UserDto>> findAllUsers(
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
  public ResponseEntity<UserDto> findUserById(@PathVariable Long userId) {
    log.info("Fetching user with id: {}", userId);
    return ResponseEntity.ok(userService.findById(userId));
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(
    @Valid @RequestBody UserRequestDto userRequestDto
  ) {
    log.info("Creating user: {}", userRequestDto);
    return ResponseEntity.ok(userService.create(userRequestDto));
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserDto> updateUser(
    @PathVariable Long userId,
    @Valid @RequestBody UserUpdateDto userUpdateDto
  ) {
    log.info("Updating user with id: {}", userId);
    return ResponseEntity.ok(userService.update(userId, userUpdateDto));
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    log.info("Deleting user with id: {}", userId);
    userService.delete(userId);
    return ResponseEntity.noContent().build();
  }
}
