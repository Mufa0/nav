package com.five.nav.service;

import com.five.nav.domain.User;
import com.five.nav.enums.Action;
import com.five.nav.response.UserAuditResponse;
import java.util.List;

public interface UserAuditServiceInterface {

  boolean createAudit(User user, String message, Action action);

  List<UserAuditResponse> findUserAuditsForUser(User user);

  UserAuditResponse findAuditForUser(long id, User user);

}
