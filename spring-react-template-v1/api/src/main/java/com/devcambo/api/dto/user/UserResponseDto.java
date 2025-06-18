package com.devcambo.api.dto.user;

public record UserResponseDto(
  Long userId,
  String username,
  String email,
  String profilePicture
) {}
