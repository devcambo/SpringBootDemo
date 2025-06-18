package com.devcambo.api.service.impl;

import com.devcambo.api.dto.user.UserRequestDto;
import com.devcambo.api.dto.user.UserResponseDto;
import com.devcambo.api.dto.user.UserUpdateDto;
import com.devcambo.api.entity.User;
import com.devcambo.api.exception.ResourceNotFoundException;
import com.devcambo.api.mapper.UserMapper;
import com.devcambo.api.repository.RoleRepository;
import com.devcambo.api.repository.UserRepository;
import com.devcambo.api.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final PasswordEncoder encoder;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  public Page<UserResponseDto> findAll(Pageable pageable) {
    Page<User> users = userRepository.findAll(pageable);
    return users.map(UserMapper::mapToUserDto);
  }

  @Override
  public UserResponseDto findById(Long userId) {
    return UserMapper.mapToUserDto(getUserById(userId));
  }

  @Override
  public void create(UserRequestDto userRequestDto) {
    User user = UserMapper.mapToUser(userRequestDto);
    user.setPassword(encoder.encode(user.getPassword()));
    roleRepository.findByName("ROLE_USER").ifPresent(role -> user.setRoles(Set.of(role)));
    userRepository.save(user);
  }

  @Override
  public void update(Long userId, UserUpdateDto userUpdateDto) {
    User existingUser = getUserById(userId);
    existingUser.setUsername(userUpdateDto.username());
    userRepository.save(existingUser);
  }

  @Override
  public void delete(Long userId) {
    getUserById(userId);
    userRepository.deleteById(userId);
  }

  private User getUserById(Long userId) {
    return userRepository
      .findById(userId)
      .orElseThrow(() ->
        new ResourceNotFoundException("User", "userId", userId.toString())
      );
  }
}
