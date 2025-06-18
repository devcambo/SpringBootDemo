package com.devcambo.api.service;

import com.devcambo.api.dto.auth.LoginRequestDto;
import com.devcambo.api.dto.auth.LoginResponseDto;
import com.devcambo.api.dto.auth.RegisterRequestDto;

public interface AuthService {
  void register(RegisterRequestDto registerRequestDto);
  LoginResponseDto login(LoginRequestDto loginRequestDto);
}
