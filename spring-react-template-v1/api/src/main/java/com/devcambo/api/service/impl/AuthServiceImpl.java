package com.devcambo.api.service.impl;

import com.devcambo.api.dto.auth.LoginRequestDto;
import com.devcambo.api.dto.auth.LoginResponseDto;
import com.devcambo.api.dto.auth.RegisterRequestDto;
import com.devcambo.api.entity.User;
import com.devcambo.api.repository.RoleRepository;
import com.devcambo.api.repository.UserRepository;
import com.devcambo.api.security.TokenService;
import com.devcambo.api.service.AuthService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder encoder;
  private final TokenService tokenService;
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
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequestDto.email(),
        loginRequestDto.password()
      )
    );
    String subject = authentication.getName();
    String roles = authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));
    String jwtToken = tokenService.generateJwtToken(subject, roles);
    return new LoginResponseDto(jwtToken);
  }
}
