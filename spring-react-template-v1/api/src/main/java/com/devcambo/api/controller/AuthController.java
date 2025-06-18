package com.devcambo.api.controller;

import com.devcambo.api.dto.auth.LoginRequestDto;
import com.devcambo.api.dto.auth.LoginResponseDto;
import com.devcambo.api.dto.auth.RegisterRequestDto;
import com.devcambo.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
