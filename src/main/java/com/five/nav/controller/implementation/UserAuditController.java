package com.five.nav.controller.implementation;

import com.five.nav.controller.UserAuditControllerInterface;
import com.five.nav.domain.User;
import com.five.nav.exception.UserNotAuthenticatedException;
import com.five.nav.repository.UserRepository;
import com.five.nav.response.UserAuditResponse;
import com.five.nav.service.UserAuditServiceInterface;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class UserAuditController implements UserAuditControllerInterface {

  UserAuditServiceInterface userAuditService;

  UserRepository userRepository;

  @Override
  public List<UserAuditResponse> getAllUserAuditsForUser(Principal principal) {
    log.info(String.format("Getting all audits for user: %s", principal.getName()));
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      log.info(String.format("Finding all audits for user: %s !",
          user.get().getEmail()));
      return userAuditService.findUserAuditsForUser(user.get());
    }else{
      log.error("User not authenticated but accessed protected URL!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }

  @Override
  public UserAuditResponse getAuditForUser(long id, Principal principal) {
    log.info(String.format("Getting audit for user: %s", principal.getName()));
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      log.info(String.format("Finding audit for user: %s !",
          user.get().getEmail()));
      return userAuditService.findAuditForUser(id,user.get());
    }else{
      log.error("User not authenticated but accessed protected URL!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }
}
