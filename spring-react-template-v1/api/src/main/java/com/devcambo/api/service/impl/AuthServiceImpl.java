package com.devcambo.api.service.impl;

import com.devcambo.api.dto.auth.LoginRequestDto;
import com.devcambo.api.dto.auth.LoginResponseDto;
import com.devcambo.api.dto.auth.RegisterRequestDto;
import com.devcambo.api.entity.User;
import com.devcambo.api.repository.RoleRepository;
import com.devcambo.api.repository.UserRepository;
import com.devcambo.api.service.AuthService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder encoder;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

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
    return null;
  }
}
