package com.five.nav.audit;

import com.five.nav.domain.Article;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "article_audit")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  long id;

  @Enumerated(EnumType.STRING)
  Action action;

  @Column(name = "timestamp")
  @Temporal(TemporalType.TIMESTAMP)
  Date timestamp;

  @Column(name = "message")
  String message;

  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;

  @ManyToOne
  @JoinColumn(name = "article_id")
  Article article;


  
}
