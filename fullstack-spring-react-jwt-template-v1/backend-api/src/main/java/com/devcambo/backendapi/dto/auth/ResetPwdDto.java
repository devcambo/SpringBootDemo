package com.devcambo.backendapi.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ResetPwdDto(
  @NotEmpty(message = "Password is required!")
  @Size(min = 5, max = 100, message = "Password must be between 5 and 100 characters!")
  String newPassword,
  @NotEmpty(message = "Password is required!")
  @Size(min = 5, max = 100, message = "Password must be between 5 and 100 characters!")
  String confirmPassword
) {}
