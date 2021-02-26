package com.five.nav.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "_group")
@Data
@Builder
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  long id;

  @Column(name = "name")
  String name;

  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;

  @ManyToOne
  @JoinColumn(name = "article_id")
  Article article;

}
