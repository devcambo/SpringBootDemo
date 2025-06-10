package com.devcambo.crudjpa.service.impl;

import com.devcambo.crudjpa.dto.auth.LoginRequestDto;
import com.devcambo.crudjpa.dto.auth.LoginResponseDto;
import com.devcambo.crudjpa.dto.auth.RegisterDto;
import com.devcambo.crudjpa.dto.user.UserDto;
import com.devcambo.crudjpa.entity.User;
import com.devcambo.crudjpa.mapper.UserMapper;
import com.devcambo.crudjpa.repository.RoleRepository;
import com.devcambo.crudjpa.repository.UserRepository;
import com.devcambo.crudjpa.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

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
        return null;
    }
}
