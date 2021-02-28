package com.five.nav.domain;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "_group")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  long id;

  @Column(name = "name")
  String name;

  @ManyToOne
  @JoinColumn(name = "user_id")
  User user;

  @ManyToMany
  @JoinTable(name = "article_group",
  joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
  inverseJoinColumns = @JoinColumn(name = "groupId", referencedColumnName = "id"))
  List<Article> articles;

}
