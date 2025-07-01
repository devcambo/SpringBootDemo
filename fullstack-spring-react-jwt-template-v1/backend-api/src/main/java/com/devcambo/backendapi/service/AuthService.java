package com.devcambo.backendapi.service;

import com.devcambo.backendapi.dto.auth.LoginDto;
import com.devcambo.backendapi.dto.auth.RegisterDto;
import com.devcambo.backendapi.dto.auth.TokenDto;

public interface AuthService {
  void register(RegisterDto registerDto);
  TokenDto login(LoginDto loginDto);
}
