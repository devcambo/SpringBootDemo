package com.devcambo.backendapi.dto.user;

import java.time.Instant;

public record ProfileDto(
  Long id,
  String username,
  String email,
  String roles,
  Instant createdAt,
  Instant updatedAt
) {}
