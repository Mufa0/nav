package com.five.nav.security.service;

import com.five.nav.domain.User;
import com.five.nav.exception.UserNotAuthenticatedException;
import com.five.nav.repository.UserRepository;
import com.five.nav.security.domain.SecurityUser;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityUserService implements UserDetailsService {

  UserRepository userRepository;

  @Override
  public SecurityUser loadUserByUsername(String email){
    Optional<User> user = userRepository.findByEmail(email);
    if(user.isPresent()){
      return new SecurityUser(user.get());
    }else{
      throw new UserNotAuthenticatedException(email);
    }
  }
}
