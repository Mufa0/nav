package com.five.nav.service.implementation;

import com.five.nav.domain.Article;
import com.five.nav.domain.User;
import com.five.nav.enums.Action;
import com.five.nav.enums.UserStatus;
import com.five.nav.exception.ArticleNotFoundException;
import com.five.nav.exception.UserAlreadyExistsException;
import com.five.nav.exception.UserNotAuthenticatedException;
import com.five.nav.mapper.UserMapper;
import com.five.nav.repository.ArticleRepository;
import com.five.nav.repository.UserRepository;
import com.five.nav.request.UserRequest;
import com.five.nav.response.UserResponse;
import com.five.nav.service.UserAuditServiceInterface;
import com.five.nav.service.UserServiceInterface;
import java.security.Principal;
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

  ArticleRepository articleRepository;

  UserAuditServiceInterface userAuditService;

  UserMapper userMapper;

  @Override
  public UserResponse registerUser(UserRequest userRequest) {
    if(userRepository.findByEmail(userRequest.getEmail()).isEmpty() ){
      User user = userMapper.from(userRequest);
      user = userRepository.save(user);

      String message = String.format("User with email: %s and role: %s saved to database with id:"
              + " %d!",
          user.getEmail(), user.getRole().toString(), user.getId());

      userAuditService.createAudit(user,message, Action.CREATE);
      return userMapper.from(user);

    }else{

      log.error(String.format("User with email: %s already exists!",userRequest.getEmail()));
      throw new UserAlreadyExistsException(userRequest.getEmail());
    }
  }

  @Override
  public List<UserResponse> getAllUsers() {
    List<User> users = userRepository.findAll();
    log.info(String.format("Found %d users in database!", users.size()));
    return users.stream().map(user -> userMapper.from(user)).collect(Collectors.toList());
  }

  @Override
  public UserResponse getUser(Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      return userMapper.from(user.get());
    }else{
      log.error("User not authenticated but managed to access protected API!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }

  @Override
  public UserResponse likeArticle(long id, Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      Optional<Article> article = articleRepository.findById(id);
      if(article.isPresent()){

        user.get().getLikedArticles().add(article.get());

        return userMapper.from(userRepository.save(user.get()));
      }else{
        log.error(String.format("Article: %d not found!", id));
        throw new ArticleNotFoundException(id);
      }
    }else{
      log.error("User not authenticated but managed to access protected API!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }

  @Override
  public void deleteUser(Principal principal) {
    Optional<User> user = userRepository.findByEmail(principal.getName());
    if(user.isPresent()){
      log.info(String.format("User with email: %s is being deleted!", user.get().getEmail()));
      user.get().setStatus(UserStatus.DELETED);
      userRepository.save(user.get());
      userAuditService.createAudit(user.get(), "User deleted!", Action.DELETE);

    }else{
      log.error("User not authenticated but managed to access protected API!");
      throw new UserNotAuthenticatedException(principal.getName());
    }
  }
}
