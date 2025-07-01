package com.devcambo.backendapi.service.impl;

import com.devcambo.backendapi.dto.UserCreateDto;
import com.devcambo.backendapi.dto.UserDto;
import com.devcambo.backendapi.dto.UserUpdateDto;
import com.devcambo.backendapi.entity.User;
import com.devcambo.backendapi.exception.ResourceNotFoundException;
import com.devcambo.backendapi.mapper.UserMapper;
import com.devcambo.backendapi.repository.UserRepository;
import com.devcambo.backendapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public Page<UserDto> findAll(Pageable pageable) {
    Page<User> users = userRepository.findAll(pageable);
    return users.map(UserMapper::mapToUserDto);
  }

  @Override
  public UserDto findById(Long userId) {
    return UserMapper.mapToUserDto(getUserById(userId));
  }

  @Override
  public void create(UserCreateDto userCreateDto) {
    userRepository.save(UserMapper.mapToUser(userCreateDto));
  }

  @Override
  public void update(Long userId, UserUpdateDto userUpdateDto) {
    User existingUser = getUserById(userId);
    existingUser.setUsername(userUpdateDto.username());
    userRepository.save(existingUser);
  }

  @Override
  public void delete(Long userId) {
    userRepository.delete(getUserById(userId));
  }

  private User getUserById(Long userId) {
    return userRepository
      .findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
  }
}
