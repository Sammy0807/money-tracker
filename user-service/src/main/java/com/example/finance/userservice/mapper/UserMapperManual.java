package com.example.finance.userservice.mapper;

import com.example.finance.userservice.domain.User;
import com.example.finance.userservice.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class UserMapperManual implements UserMapper {

  @Override
  public UserDto toDto(User user) {
    if (user == null) return null;
    return new UserDto(
        user.getId(),
        user.getEmail(),
        user.getName(),
        user.getLocale(),
        user.getCurrency(),
        user.getTimezone(),
        user.getFeatureFlagsJson()
    );
  }

  @Override
  public List<UserDto> toDtoList(List<User> users) {
    if (users == null) return null;
    return users.stream().map(this::toDto).collect(Collectors.toList());
  }
}
