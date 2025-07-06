package com.devcambo.backendapi.service;

import com.devcambo.backendapi.dto.auth.*;

public interface AuthService {
  void register(RegisterDto registerDto);
  TokenDto login(LoginDto loginDto);
  /**
   * See <a href="https://supertokens.com/blog/implementing-a-forgot-password-flow">https://supertokens.com</a> for more information.
   */
  void forgotPassword(ForgotPwdDto forgotPwdDto);
  void resetPassword(String token, ResetPwdDto resetPwdDto);
}
