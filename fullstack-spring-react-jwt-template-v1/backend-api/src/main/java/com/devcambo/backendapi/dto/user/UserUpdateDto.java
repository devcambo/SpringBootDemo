package com.devcambo.backendapi.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(
  @NotEmpty(message = "Username is required!")
  @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters!")
  String username,

  @Size(min = 3, max = 100, message = "Profile must be between 3 and 100 characters!")
  String profilePicture
) {}
