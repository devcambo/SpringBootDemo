package com.devcambo.backendapi.mapper;

import com.devcambo.backendapi.dto.user.UserCreateDto;
import com.devcambo.backendapi.dto.user.UserDto;
import com.devcambo.backendapi.entity.User;

public class UserMapper {

  public static UserDto mapToUserDto(User user) {
    return new UserDto(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getRoles(),
      user.getCreatedAt()
    );
  }

  public static User mapToUser(UserCreateDto userCreateDto) {
    User user = new User();
    user.setUsername(userCreateDto.username());
    user.setEmail(userCreateDto.email());
    user.setPassword(userCreateDto.password());
    user.setRoles(userCreateDto.roles());
    return user;
  }
}
