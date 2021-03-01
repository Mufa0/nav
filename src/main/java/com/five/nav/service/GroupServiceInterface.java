package com.five.nav.service;


import com.five.nav.domain.User;
import com.five.nav.request.GroupRequest;
import com.five.nav.response.GroupResponse;
import java.util.List;

public interface GroupServiceInterface {

  GroupResponse createGroup(GroupRequest request, User user);

  GroupResponse updateGroup(long id,GroupRequest request, User user);

  List<GroupResponse> getAllGroupsForUser(User user);

  GroupResponse getGroup(long id, User user);

  void deleteGroup(long id, User user);

}
