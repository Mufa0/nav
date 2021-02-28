package com.five.nav.controller;

import com.five.nav.request.GroupRequest;
import com.five.nav.response.GroupResponse;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/groups")
public interface GroupControllerInterface {

  @PostMapping
  GroupResponse createGroup(@Valid @RequestBody GroupRequest groupRequest, Principal principal);

  @PutMapping("/{id}")
  GroupResponse updateGroup(@PathVariable("id") long id,
      @Valid @RequestBody GroupRequest groupRequest,
      Principal principal);

  @GetMapping("/user")
  List<GroupResponse> getAllGroupsForUser( Principal principal);

  @GetMapping("/{id}")
  GroupResponse getGroup(@PathVariable("id") long id, Principal principal);

  @DeleteMapping("/{id}")
  void deleteGroup(@PathVariable("id") long id, Principal principal);


}
