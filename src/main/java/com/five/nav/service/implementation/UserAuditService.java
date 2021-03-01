package com.five.nav.service.implementation;


import com.five.nav.audit.UserAudit;
import com.five.nav.domain.User;
import com.five.nav.enums.Action;
import com.five.nav.mapper.UserAuditMapper;
import com.five.nav.repository.UserAuditRepository;
import com.five.nav.response.UserAuditResponse;
import com.five.nav.service.UserAuditServiceInterface;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserAuditService implements UserAuditServiceInterface {
  UserAuditRepository userAuditRepository;
  UserAuditMapper mapper;
  @Override
  public boolean createAudit(User user, String message, Action action) {
    UserAudit audit =
        UserAudit.builder().action(action).user(user).timestamp(Calendar.getInstance().getTime()).build();

    try{
      userAuditRepository.save(audit);
      log.info(String.format("Audit for user: %d with action: %s and message: %s "
          + "created!",user.getId(), action, message));
      return true;
    }catch (Exception e){
      log.info(String.format("Audit for user: %d with action: %s and message: %s "
          + "not created!",user.getId(), action, message));
      return false;
    }
  }

  @Override
  public List<UserAuditResponse> findUserAuditsForUser(User user) {
    List<UserAudit> audits = userAuditRepository.findAllForUser(user.getId());

    log.info(String.format("Fetched %d audits for user articles!", audits.size()));

    return audits.stream().map(audit-> mapper.from(audit)).collect(Collectors.toList());

  }

  @Override
  public UserAuditResponse findAuditForUser(long id, User user) {
    UserAudit audit = userAuditRepository.findById(id, user.getId());
    log.info(String.format("Fetched audit %d for user %d!", id, user.getId()));
    return mapper.from(audit);
  }
}
