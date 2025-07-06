package com.devcambo.backendapi.service.impl;

import com.devcambo.backendapi.constant.AppConstants;
import com.devcambo.backendapi.dto.auth.*;
import com.devcambo.backendapi.entity.PwdResetToken;
import com.devcambo.backendapi.entity.User;
import com.devcambo.backendapi.exception.CustomNoSuchAlgorithmException;
import com.devcambo.backendapi.exception.PasswordChangeIllegalArgumentException;
import com.devcambo.backendapi.exception.ResourceNotFoundException;
import com.devcambo.backendapi.exception.TokenExpiredException;
import com.devcambo.backendapi.repository.PwdResetTokenRepository;
import com.devcambo.backendapi.repository.UserRepository;
import com.devcambo.backendapi.security.TokenProvider;
import com.devcambo.backendapi.service.AuthService;
import com.devcambo.backendapi.service.EmailService;
import jakarta.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder encoder;
  private final TokenProvider tokenProvider;
  private final EmailService emailService;
  private final UserRepository userRepository;
  private final PwdResetTokenRepository pwdResetTokenRepository;

  @Value("${app.cors.allowed-origins}")
  private List<String> allowedOrigins;

  @Override
  public void register(RegisterDto registerDto) {
    User user = new User();
    user.setUsername(registerDto.username());
    user.setEmail(registerDto.email());
    user.setPassword(encoder.encode(registerDto.password()));
    user.setRoles("SUPER_ADMIN,ADMIN,USER");
    userRepository.save(user);
  }

  @Override
  public TokenDto login(LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password())
    );
    String subject = authentication.getName();
    String roles = authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));
    String token = tokenProvider.generateToken(subject, roles);
    return new TokenDto(token);
  }

  @Transactional
  @Override
  public void forgotPassword(ForgotPwdDto forgotPwdDto) {
    User user = userRepository
      .findByEmail(forgotPwdDto.email())
      .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

    Instant now = Instant.now();
    String token = generateToken(64);
    PwdResetToken pwdResetToken = new PwdResetToken();
    pwdResetToken.setToken(tokenHasher(token));
    pwdResetToken.setTokenExpiry(now.plus(1, ChronoUnit.MINUTES));
    user.setPasswordResetToken(pwdResetToken);
    userRepository.save(user);

    String resetPwdLink = String.format(
      "%s/reset-password?token=%s",
      allowedOrigins.getFirst(),
      token
    );
    emailService.send(user.getEmail(), "Reset password link", resetPwdLink);
    log.info("Reset password link generated: {}", resetPwdLink);
  }

  @Override
  public void resetPassword(String token, ResetPwdDto resetPwdDto) {
    PwdResetToken pwdResetToken = pwdResetTokenRepository
      .findByToken(tokenHasher(token))
      .orElseThrow(() -> new ResourceNotFoundException("Token not found!"));

    if (isTokenExpired(pwdResetToken.getTokenExpiry())) {
      throw new TokenExpiredException("Token is expired!");
    }

    if (!resetPwdDto.newPassword().equals(resetPwdDto.confirmPassword())) {
      throw new PasswordChangeIllegalArgumentException(
        "New password and confirm new password do not match!"
      );
    }

    User user = userRepository
      .findByPasswordResetToken(pwdResetToken)
      .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    user.setPassword(encoder.encode(resetPwdDto.newPassword()));
    userRepository.save(user);
  }

  private static String generateToken(int length) {
    Random random = new SecureRandom();
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < length; i++) {
      result.append(
        AppConstants.ALPHABET.charAt(random.nextInt(AppConstants.ALPHABET.length()))
      );
    }
    return new String(result);
  }

  private static String tokenHasher(String token) {
    StringBuilder sb = new StringBuilder();
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hash = md.digest(token.getBytes());
      for (byte b : hash) {
        sb.append(String.format("%02x", b));
      }
    } catch (NoSuchAlgorithmException exception) {
      throw new CustomNoSuchAlgorithmException(exception.getMessage());
    }
    return sb.toString();
  }

  private static boolean isTokenExpired(final Instant tokenExpiry) {
    Instant now = Instant.now();
    return tokenExpiry.isBefore(now); // 10.30 isBefore 10.20 -> false
  }
}
