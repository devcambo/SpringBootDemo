package com.devcambo.backendapi.controller;

import com.devcambo.backendapi.dto.auth.*;
import com.devcambo.backendapi.service.AuthService;
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
  public ResponseEntity<Void> registerUser(@Valid @RequestBody RegisterDto registerDto) {
    log.info("Registering user: {}", registerDto.email());
    authService.register(registerDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDto> loginUser(@Valid @RequestBody LoginDto loginDto) {
    log.info("Logging in user: {}", loginDto.email());
    return ResponseEntity.ok(authService.login(loginDto));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<Void> forgotPasswordUser(
    @Valid @RequestBody ForgotPwdDto forgotPwdDto
  ) {
    log.info("Forgot password: {}", forgotPwdDto.email());
    authService.forgotPassword(forgotPwdDto);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/reset-password")
  public ResponseEntity<Void> resetPasswordUser(
    @RequestParam(name = "token") String token,
    @Valid @RequestBody ResetPwdDto resetPwdDto
  ) {
    authService.resetPassword(token, resetPwdDto);
    return ResponseEntity.ok().build();
  }
}
