package com.devcambo.backendapi.controller;

import com.devcambo.backendapi.dto.auth.LoginDto;
import com.devcambo.backendapi.dto.auth.RegisterDto;
import com.devcambo.backendapi.dto.auth.TokenDto;
import com.devcambo.backendapi.service.AuthService;
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
}
