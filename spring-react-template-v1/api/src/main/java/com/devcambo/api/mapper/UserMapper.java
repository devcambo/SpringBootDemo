package com.devcambo.api.mapper;

import com.devcambo.api.dto.user.UserRequestDto;
import com.devcambo.api.dto.user.UserResponseDto;
import com.devcambo.api.entity.User;

public class UserMapper {

  public static UserResponseDto mapToUserDto(User user) {
    return new UserResponseDto(
      user.getUserId(),
      user.getUsername(),
      user.getEmail(),
      user.getProfilePicture()
    );
  }

  public static User mapToUser(UserRequestDto userRequestDto) {
    User user = new User();
    user.setUsername(userRequestDto.username());
    user.setEmail(userRequestDto.email());
    user.setPassword(userRequestDto.password());
    return user;
  }
}
