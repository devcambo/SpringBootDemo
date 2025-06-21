package com.devcambo.api.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ChangePwdReqDto(
  @NotEmpty(message = "Old password is required!")
  @Size(min = 5, max = 100, message = "Password must be between 5 and 100 characters!")
  String oldPassword,

  @NotEmpty(message = "New password is required!")
  @Size(min = 5, max = 100, message = "Password must be between 5 and 100 characters!")
  String newPassword,

  @NotEmpty(message = "Confirm new password is required!")
  @Size(min = 5, max = 100, message = "Password must be between 5 and 100 characters!")
  String confirmNewPassword
) {}
