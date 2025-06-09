package com.devcambo.crudjpa.service.impl;

import com.devcambo.crudjpa.dto.user.UserDto;
import com.devcambo.crudjpa.dto.user.UserRequestDto;
import com.devcambo.crudjpa.dto.user.UserUpdateDto;
import com.devcambo.crudjpa.entity.User;
import com.devcambo.crudjpa.exception.ResourceNotFoundException;
import com.devcambo.crudjpa.mapper.UserMapper;
import com.devcambo.crudjpa.repository.UserRepository;
import com.devcambo.crudjpa.service.UserService;
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
  public UserDto findById(Long id) {
    return UserMapper.mapToUserDto(getUserById(id));
  }

  @Override
  public UserDto create(UserRequestDto userRequestDto) {
    return UserMapper.mapToUserDto(
      userRepository.save(UserMapper.mapToUser(userRequestDto))
    );
  }

  @Override
  public UserDto update(Long id, UserUpdateDto userUpdateDto) {
    User existingUser = getUserById(id);
    existingUser.setUsername(userUpdateDto.username());
    return UserMapper.mapToUserDto(userRepository.save(existingUser));
  }

  @Override
  public void delete(Long id) {
    User user = getUserById(id);
    userRepository.deleteById(user.getUserId());
  }

  private User getUserById(Long id) {
    return userRepository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
  }
}
