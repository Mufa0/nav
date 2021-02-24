package com.five.nav.controller.implementation;

import com.five.nav.controller.UserControllerInterface;
import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;
import com.five.nav.service.UserServiceInterface;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class UserController implements UserControllerInterface {

  UserServiceInterface userService;

  @Override
  public UserResponse registerUser(@Valid UserRequest user) {
    return userService.registerUser(user);
  }
}
