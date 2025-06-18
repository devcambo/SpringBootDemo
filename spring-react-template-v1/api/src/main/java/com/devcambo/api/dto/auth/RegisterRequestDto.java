package com.devcambo.api.dto.auth;

import com.devcambo.api.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
  @NotEmpty(message = "Username is required!")
  @Size(min = 3, max = 100, message = "Username must be between 3 and 100 characters!")
  String username,

  @NotEmpty(message = "Email is required!")
  @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters!")
  @Email
  @UniqueEmail
  String email,

  @NotEmpty(message = "Password is required!")
  @Size(min = 5, max = 100, message = "Password must be between 5 and 100 characters!")
  String password
) {}
