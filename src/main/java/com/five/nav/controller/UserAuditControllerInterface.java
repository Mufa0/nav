package com.five.nav.controller;

import com.five.nav.response.UserAuditResponse;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user-audits")
public interface UserAuditControllerInterface {

  @GetMapping("/user")
  List<UserAuditResponse> getAllUserAuditsForUser(Principal principal);

  @GetMapping("/{id}")
  UserAuditResponse getAuditForUser(@PathVariable("id") long id, Principal principal);
}
