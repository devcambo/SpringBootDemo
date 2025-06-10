package com.devcambo.crudjpa.service;

import com.devcambo.crudjpa.dto.auth.LoginRequestDto;
import com.devcambo.crudjpa.dto.auth.LoginResponseDto;
import com.devcambo.crudjpa.dto.auth.RegisterDto;
import com.devcambo.crudjpa.dto.user.UserDto;
import jakarta.validation.Valid;

public interface AuthService {
    UserDto register(@Valid RegisterDto registerDto);
    LoginResponseDto login(@Valid LoginRequestDto loginRequestDto);
}
