package com.devcambo.backendapi.dto.user;

import java.time.Instant;

public record UserDto(
  Long id,
  String username,
  String email,
  String profilePicture,
  String roles,
  Instant createdAt
) {}
