package com.devcambo.crudjpa.controller;

import com.devcambo.crudjpa.dto.auth.LoginRequestDto;
import com.devcambo.crudjpa.dto.auth.LoginResponseDto;
import com.devcambo.crudjpa.dto.auth.RegisterDto;
import com.devcambo.crudjpa.dto.user.UserDto;
import com.devcambo.crudjpa.service.AuthService;
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
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        log.info("Registering user: {}", registerDto.email());
        return ResponseEntity.ok(authService.register(registerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        log.info("Logging in user: {}", loginRequestDto.email());
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

}
