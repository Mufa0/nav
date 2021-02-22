package com.five.nav.domain;

import com.five.nav.enums.ArticleStatus;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "article")
@Data
@Builder
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  long id;

  @Column(name = "title")
  String title;

  @Column(name = "url")
  String url;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  ArticleStatus status;

  @ManyToOne
  @JoinColumn(name = "author")
  User author;

  @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
  List<Group> groups;

  @ManyToMany(mappedBy = "likedArticles")
  List<User> userWhoLiked;

}
