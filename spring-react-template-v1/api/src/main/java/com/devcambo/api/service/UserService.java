package com.devcambo.api.service;

import com.devcambo.api.dto.user.UserRequestDto;
import com.devcambo.api.dto.user.UserResponseDto;
import com.devcambo.api.dto.user.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
  Page<UserResponseDto> findAll(Pageable pageable);
  UserResponseDto findById(Long userId);
  void create(UserRequestDto userRequestDto);
  void update(Long userId, UserUpdateDto userUpdateDto);
  void delete(Long userId);
  UserResponseDto getCurrentUser(String email);
}
