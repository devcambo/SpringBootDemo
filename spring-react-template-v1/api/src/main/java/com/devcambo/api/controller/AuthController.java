package com.devcambo.api.controller;

import com.devcambo.api.dto.auth.*;
import com.devcambo.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<Void> registerUser(
    @Valid @RequestBody RegisterRequestDto registerRequestDto
  ) {
    log.info("Registering user: {}", registerRequestDto.email());
    authService.register(registerRequestDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> loginUser(
    @Valid @RequestBody LoginRequestDto loginRequestDto
  ) {
    log.info("Logging in user: {}", loginRequestDto.email());
    return ResponseEntity.ok(authService.login(loginRequestDto));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<Void> forgotPassword(
    @Valid @RequestBody ForgotPwdRequest forgotPwdRequest
  ) {
    log.info("Creating password reset token for email: {}", forgotPwdRequest.email());
    authService.createPasswordResetToken(forgotPwdRequest.email());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Void> resetPassword(
    @RequestParam String token,
    @Valid @RequestBody ResetPwdRequest request
  ) {
    log.info("Resetting password for token: {}", token);
    authService.resetPassword(token, request.newPassword());
    return ResponseEntity.ok().build();
  }
}
