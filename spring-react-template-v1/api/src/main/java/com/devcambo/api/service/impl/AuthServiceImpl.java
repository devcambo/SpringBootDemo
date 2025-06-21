package com.devcambo.api.service.impl;

import com.devcambo.api.dto.auth.LoginRequestDto;
import com.devcambo.api.dto.auth.LoginResponseDto;
import com.devcambo.api.dto.auth.RegisterRequestDto;
import com.devcambo.api.entity.PasswordResetToken;
import com.devcambo.api.entity.User;
import com.devcambo.api.exception.PasswordChangeIllegalArgumentException;
import com.devcambo.api.exception.ResourceNotFoundException;
import com.devcambo.api.repository.PasswordResetTokenRepository;
import com.devcambo.api.repository.RoleRepository;
import com.devcambo.api.repository.UserRepository;
import com.devcambo.api.security.jwt.TokenService;
import com.devcambo.api.service.AuthService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder encoder;
  private final TokenService tokenService;
  private final JavaMailSender javaMailSender;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordResetTokenRepository passwordResetTokenRepository;

  @Value("${reset-pwd-url}")
  private String resetPwdUrl;

  @Override
  public void register(RegisterRequestDto registerRequestDto) {
    User user = new User();
    user.setUsername(registerRequestDto.username());
    user.setEmail(registerRequestDto.email());
    user.setPassword(encoder.encode(registerRequestDto.password()));
    roleRepository.findByName("ROLE_USER").ifPresent(role -> user.setRoles(Set.of(role)));
    userRepository.save(user);
  }

  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequestDto.email(),
        loginRequestDto.password()
      )
    );
    var loggedInUser = (User) authentication.getPrincipal();
    String subject = loggedInUser.getEmail();
    String roles = extractRoles(authentication);
    String jwtToken = tokenService.generateJwtToken(subject, roles);
    return new LoginResponseDto(jwtToken);
  }

  @Override
  public void createPasswordResetToken(String email) {
    User user = userRepository
      .findByEmail(email)
      .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

    passwordResetTokenRepository.deleteByUser(user);

    String token = UUID.randomUUID().toString();
    String hashedToken = hashToken(token);

    PasswordResetToken pwdResetToken = new PasswordResetToken();
    pwdResetToken.setToken(hashedToken);
    pwdResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(2));
    pwdResetToken.setUser(user);
    passwordResetTokenRepository.save(pwdResetToken);

    sendResetLink(user.getEmail(), token);
  }

  @Override
  public void resetPassword(String token, String newPassword) {
    String hashedToken = hashToken(token);
    PasswordResetToken pwdResetToken = passwordResetTokenRepository
      .findByToken(hashedToken)
      .orElseThrow(() ->
        new ResourceNotFoundException("PasswordResetToken", "token", token)
      );

    if (pwdResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
      passwordResetTokenRepository.delete(pwdResetToken);
      throw new PasswordChangeIllegalArgumentException("Token has expired!");
    }

    User user = pwdResetToken.getUser();
    user.setPassword(encoder.encode(newPassword));
    userRepository.save(user);

    passwordResetTokenRepository.delete(pwdResetToken);
  }

  private static String extractRoles(Authentication authentication) {
    return authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));
  }

  private static String hashToken(String token) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(token.getBytes());
      StringBuilder hexString = new StringBuilder();

      for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }

      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error hashing token", e);
    }
  }

  private void sendResetLink(String to, String token) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject("Password Reset Request");
    message.setText(
      "Please click the link below to reset your password:\n" +
      resetPwdUrl +
      "/reset-password?token=" +
      token
    );
    javaMailSender.send(message);
  }
}
