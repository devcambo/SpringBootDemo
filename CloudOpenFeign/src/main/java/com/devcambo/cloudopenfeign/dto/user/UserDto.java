package com.devcambo.cloudopenfeign.dto.user;

public record UserDto(
  Long id,
  String email,
  String password,
  String name,
  String role,
  String avatar
) {}
