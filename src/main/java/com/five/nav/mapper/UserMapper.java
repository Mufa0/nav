package com.five.nav.mapper;

import com.five.nav.domain.Article;
import com.five.nav.domain.Group;
import com.five.nav.domain.User;
import com.five.nav.enums.Role;
import com.five.nav.enums.UserStatus;
import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;
import java.util.ArrayList;
import java.util.stream.Collectors;
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
        .role(request.getRole() != null && !request.getRole().isBlank()?
            Role.valueOf(request.getRole()) :
            Role.READER )
        .build();
  }

  public UserResponse from(User user){
    return UserResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .lastname(user.getLastname())
        .email(user.getEmail())
        .articles(user.getArticles() != null ?
            user.getArticles().stream().map(Article::getId).collect(
                Collectors.toList()):
            new ArrayList<>())
        .role(user.getRole().toString())
        .groups(user.getGroups() != null ? user.getGroups().stream().map(Group::getId).collect(
            Collectors.toList()):
            new ArrayList<>())
        .likedArticles(user.getLikedArticles() != null ?
            user.getLikedArticles().stream().map(Article::getId).collect(Collectors.toList()):
            new ArrayList<>())
        .build();
  }
}
