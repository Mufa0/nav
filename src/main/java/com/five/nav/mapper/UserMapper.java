package com.five.nav.mapper;

import com.five.nav.domain.User;
import com.five.nav.enums.Role;
import com.five.nav.enums.UserStatus;
import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
  BCryptPasswordEncoder bCryptPasswordEncoder;

  public User from(UserRequest request){
    return User.builder()
        .name(request.getName())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(bCryptPasswordEncoder.encode(request.getPassword()))
        .status(UserStatus.ACTIVE)
        .role(Role.READER)
        .build();
  }

  public UserResponse from(User user){
    return UserResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .lastname(user.getLastname())
        .email(user.getEmail())
        .build();
  }
}
