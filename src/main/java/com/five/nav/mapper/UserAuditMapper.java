package com.five.nav.mapper;

import com.five.nav.audit.UserAudit;
import com.five.nav.response.UserAuditResponse;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserAuditMapper {

  public UserAuditResponse from(UserAudit audit) {
    return UserAuditResponse.builder().action(audit.getAction().toString())
        .commiterEmail(audit.getUser().getEmail()).id(audit.getId()).message(audit.getMessage())
        .timestamp(audit.getTimestamp().toString()).build();
  }

}
