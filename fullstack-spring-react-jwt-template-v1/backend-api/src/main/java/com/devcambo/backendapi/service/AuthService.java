package com.devcambo.backendapi.service;

import com.devcambo.backendapi.dto.auth.*;

public interface AuthService {
  void register(RegisterDto registerDto);
  TokenDto login(LoginDto loginDto);
  void forgotPassword(ForgotPwdDto forgotPwdDto);
  void resetPassword(String token, ResetPwdDto resetPwdDto);
}
