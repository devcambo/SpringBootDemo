package com.devcambo.crudjpa.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        @NotEmpty(message = "Email is required!")
        @Size(min = 5, max = 100, message = "Email must be between 5 and 100 characters!")
        @Email
    String email,
        @NotEmpty(message = "Password is required!")
        @Size(min = 5, max = 100, message = "Password must be between 5 and 100 characters!")
    String password
) {
}
