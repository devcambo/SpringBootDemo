package com.devcambo.backendapi.service;

import com.devcambo.backendapi.dto.UserCreateDto;
import com.devcambo.backendapi.dto.UserDto;
import com.devcambo.backendapi.dto.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
  Page<UserDto> findAll(Pageable pageable);
  UserDto findById(Long userId);
  void create(UserCreateDto userCreateDto);
  void update(Long userId, UserUpdateDto userUpdateDto);
  void delete(Long userId);
}
