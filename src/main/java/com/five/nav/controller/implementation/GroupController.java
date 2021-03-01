package com.five.nav.controller.implementation;

import com.five.nav.controller.GroupControllerInterface;
import com.five.nav.domain.User;
import com.five.nav.exception.UserNotAuthenticatedException;
import com.five.nav.repository.UserRepository;
import com.five.nav.request.GroupRequest;
import com.five.nav.response.GroupResponse;
import com.five.nav.service.GroupServiceInterface;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class GroupController implements GroupControllerInterface {

  GroupServiceInterface groupService;

  UserRepository userRepository;

  @Override
  public GroupResponse createGroup(@Valid GroupRequest groupRequest, Principal principal) {
    Optional<User> user  = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      log.info(String.format("Creating group for user: %s",principal.getName()));
      return groupService.createGroup(groupRequest, user.get());
    }else{
      log.error("User not authenticated but accessed protected API!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }

  @Override
  public GroupResponse updateGroup(long id, @Valid GroupRequest groupRequest, Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      log.info(String.format("Updating group: %d for user: %s",id,principal.getName()));
      return groupService.updateGroup(id,groupRequest, user.get());
    }else{
      log.error("User not authenticated but accessed protected API!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }

  @Override
  public List<GroupResponse> getAllGroupsForUser(Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      log.info(String.format("Getting all groups for user: %s", principal.getName()));
      return groupService.getAllGroupsForUser(user.get());
    }else{
      log.error("User not authenticated but accessed protected API!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }

  @Override
  public GroupResponse getGroup(long id, Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      log.info(String.format("Getting group with id: %d for on user: %s", id, principal.getName()));
      return groupService.getGroup(id, user.get());
    }else{
      log.error("User not authenticated but accessed protected API!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }

  @Override
  public void deleteGroup(long id, Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      log.info(String.format("Deleting group with id: %d for on user: %s", id,
          principal.getName()));
      groupService.deleteGroup(id, user.get());
    }else{
      log.error("User not authenticated but accessed protected API!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }
}
