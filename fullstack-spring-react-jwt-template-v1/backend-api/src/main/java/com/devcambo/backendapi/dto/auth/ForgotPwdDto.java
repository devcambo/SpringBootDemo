package com.devcambo.backendapi.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ForgotPwdDto(
  @NotEmpty(message = "Email is required!")
  @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters!")
  @Email
  String email
) {}
