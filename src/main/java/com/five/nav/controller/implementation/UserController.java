package com.five.nav.controller.implementation;

import com.five.nav.controller.UserControllerInterface;
import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;
import com.five.nav.service.UserServiceInterface;
import java.security.Principal;
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
    log.info(String.format("Registrating user with email: %s",user.getEmail()));
    return userService.registerUser(user);
  }

  @Override
  public List<UserResponse> getAllUsers() {
    log.info("Fetching all users currently in database!");
    return userService.getAllUsers();
  }

  @Override
  public UserResponse getUser(Principal principal) {
    log.info(String.format("User: %s trying to get user info!", principal.getName()));
    return userService.getUser( principal);
  }

  @Override
  public UserResponse likeArticle(long id, Principal principal) {
    log.info(String.format("User: %s linking article: %d !", principal.getName(), id));
    return userService.likeArticle(id, principal);
  }

  @Override
  public void deleteUser(Principal principal) {
    log.info(String.format("User: %s is being deleted!", principal.getName()));
    userService.deleteUser(principal);
  }


}
