package com.devcambo.backendapi.service.impl;

import com.devcambo.backendapi.dto.auth.LoginDto;
import com.devcambo.backendapi.dto.auth.RegisterDto;
import com.devcambo.backendapi.dto.auth.TokenDto;
import com.devcambo.backendapi.entity.User;
import com.devcambo.backendapi.repository.UserRepository;
import com.devcambo.backendapi.security.TokenProvider;
import com.devcambo.backendapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder encoder;
  private final TokenProvider tokenProvider;
  private final UserRepository userRepository;

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
    var token = tokenProvider.generateToken(
      authentication.getName(),
      authentication.getAuthorities().toString()
    );
    return new TokenDto(token);
  }
}
