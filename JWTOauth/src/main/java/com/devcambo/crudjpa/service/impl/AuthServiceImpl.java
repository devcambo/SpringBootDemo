package com.devcambo.crudjpa.service.impl;

import com.devcambo.crudjpa.dto.auth.LoginRequestDto;
import com.devcambo.crudjpa.dto.auth.LoginResponseDto;
import com.devcambo.crudjpa.dto.auth.RegisterDto;
import com.devcambo.crudjpa.dto.user.UserDto;
import com.devcambo.crudjpa.entity.User;
import com.devcambo.crudjpa.mapper.UserMapper;
import com.devcambo.crudjpa.repository.RoleRepository;
import com.devcambo.crudjpa.repository.UserRepository;
import com.devcambo.crudjpa.security.TokenService;
import com.devcambo.crudjpa.service.AuthService;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  private final TokenService tokenService;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public UserDto register(RegisterDto registerDto) {
    User user = new User();
    user.setUsername(registerDto.username());
    user.setEmail(registerDto.email());
    user.setPassword(encoder.encode(registerDto.password()));
    roleRepository.findByName("ROLE_USER").ifPresent(role -> user.setRoles(Set.of(role)));
    User newUser = userRepository.save(user);
    return UserMapper.mapToUserDto(newUser);
  }

  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequestDto.email(),
        loginRequestDto.password()
      )
    );
    String subject = authentication.getName();
    String permissions = authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));
    String accessToken = tokenService.generateToken(subject, permissions);
    return new LoginResponseDto(accessToken);
  }
}
