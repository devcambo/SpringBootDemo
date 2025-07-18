package com.devcambo.backendapi.service.impl;

import com.devcambo.backendapi.dto.user.ProfileDto;
import com.devcambo.backendapi.dto.user.UserCreateDto;
import com.devcambo.backendapi.dto.user.UserDto;
import com.devcambo.backendapi.dto.user.UserUpdateDto;
import com.devcambo.backendapi.entity.User;
import com.devcambo.backendapi.exception.ResourceNotFoundException;
import com.devcambo.backendapi.mapper.UserMapper;
import com.devcambo.backendapi.repository.UserRepository;
import com.devcambo.backendapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final PasswordEncoder encoder;
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
    User user = UserMapper.mapToUser(userCreateDto);
    user.setPassword(encoder.encode(userCreateDto.password()));
    userRepository.save(user);
  }

  @Override
  public void update(Long userId, UserUpdateDto userUpdateDto) {
    User existingUser = getUserById(userId);
    existingUser.setUsername(userUpdateDto.username());
    existingUser.setProfilePicture(userUpdateDto.profilePicture());
    userRepository.save(existingUser);
  }

  @Override
  public void delete(Long userId) {
    userRepository.delete(getUserById(userId));
  }

  @Override
  public ProfileDto findProfile() {
    Authentication authentication = SecurityContextHolder
      .getContext()
      .getAuthentication();
    User user = userRepository
      .findByEmail(authentication.getName())
      .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    return new ProfileDto(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getProfilePicture(),
      user.getRoles(),
      user.getCreatedAt(),
      user.getUpdatedAt()
    );
  }

  public boolean isOwner(Long userId, String email) {
    User user = getUserById(userId);
    return user.getEmail().equals(email);
  }

  private User getUserById(Long userId) {
    return userRepository
      .findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
  }
}
