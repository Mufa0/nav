package com.five.nav.controller.implementation;

import com.five.nav.controller.UserControllerInterface;
import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;
import com.five.nav.service.UserServiceInterface;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class UserController implements UserControllerInterface {

  UserServiceInterface userService;

  @Override
  public UserResponse registerUser(UserRequest user) {
    return userService.registerUser(user);
  }

  @Override
  public List<UserResponse> getAllUsers() {
    return userService.getAllUsers();
  }
}
