package com.five.nav.audit;

import com.five.nav.domain.User;
import com.five.nav.enums.Action;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "user_audit")
@Data
@Builder
public class UserAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  long id;

  @Enumerated(EnumType.ORDINAL)
  Action action;

  @Column(name = "timestamp")
  @Temporal(TemporalType.TIMESTAMP)
  Date timestamp;


  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;

}
