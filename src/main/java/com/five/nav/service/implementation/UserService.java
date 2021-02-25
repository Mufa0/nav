package com.five.nav.service.implementation;

import com.five.nav.domain.User;
import com.five.nav.exception.UserAlreadyExistsException;
import com.five.nav.mapper.UserMapper;
import com.five.nav.repository.UserRepository;
import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;
import com.five.nav.service.UserServiceInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserServiceInterface {

  UserRepository userRepository;
  UserMapper userMapper;

  @Override
  public UserResponse registerUser(UserRequest userRequest) {
    if(userRepository.findByEmail(userRequest.getEmail()).isEmpty() ){
      User user = userMapper.from(userRequest);
      return userMapper.from(userRepository.save(user));
    }else{
      throw new UserAlreadyExistsException(userRequest.getEmail());
    }
  }

  @Override
  public List<UserResponse> getAllUsers() {
    Optional<List<User>> users = Optional.of((List<User>)userRepository.findAll());
    if(users.isPresent()){
      return users.get().stream().map(user -> userMapper.from(user)).collect(Collectors.toList());
    }else{
      return new ArrayList<>();
    }
  }
}
