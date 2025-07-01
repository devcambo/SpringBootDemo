package com.devcambo.backendapi.dto;

import java.time.Instant;

public record UserDto(
  Long id,
  String username,
  String email,
  String roles,
  Instant createdAt
) {}
