package com.devcambo.crudjpa.mapper;

import com.devcambo.crudjpa.dto.user.UserDto;
import com.devcambo.crudjpa.dto.user.UserRequestDto;
import com.devcambo.crudjpa.entity.User;

public class UserMapper {

  public static UserDto mapToUserDto(User user) {
    return new UserDto(user.getUserId(), user.getUsername(), user.getEmail());
  }

  public static User mapToUser(UserRequestDto userRequestDto) {
    User user = new User();
    user.setUsername(userRequestDto.username());
    user.setEmail(userRequestDto.email());
    user.setPassword(userRequestDto.password());
    return user;
  }
}
