package com.example.finance.userservice.mapper;

import com.example.finance.userservice.domain.User;
import com.example.finance.userservice.dto.UserDto;

import java.util.List;

public interface UserMapper {
  UserDto toDto(User user);
  List<UserDto> toDtoList(List<User> users);
}
