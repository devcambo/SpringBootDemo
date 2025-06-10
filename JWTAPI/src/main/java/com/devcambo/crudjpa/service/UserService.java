package com.devcambo.crudjpa.service;

import com.devcambo.crudjpa.dto.user.UserDto;
import com.devcambo.crudjpa.dto.user.UserRequestDto;
import com.devcambo.crudjpa.dto.user.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
  Page<UserDto> findAll(Pageable pageable);
  UserDto findById(Long id);
  UserDto create(UserRequestDto userRequestDto);
  UserDto update(Long id, UserUpdateDto userUpdateDto);
  void delete(Long id);
}
