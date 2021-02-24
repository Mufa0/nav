package com.five.nav.domain;

import com.five.nav.enums.Role;
import com.five.nav.enums.UserStatus;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  long id;

  @Column(name = "name")
  String name;

  @Column(name = "lastname")
  String lastname;

  @Column(name = "email", unique = true)
  @Email
  String email;

  @Column
  String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  UserStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  Role role;

  @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
  List<Article> articles;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  List<Group> groups;

  @ManyToMany
  @JoinTable(name = "user_liked_articles",
  joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"))
  List<Article> likedArticles;
}
