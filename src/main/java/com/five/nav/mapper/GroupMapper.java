package com.five.nav.mapper;

import com.five.nav.domain.Article;
import com.five.nav.domain.Group;
import com.five.nav.domain.User;
import com.five.nav.exception.ArticleNotFoundException;
import com.five.nav.repository.ArticleRepository;
import com.five.nav.request.GroupRequest;
import com.five.nav.response.ArticleResponse;
import com.five.nav.response.GroupResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class GroupMapper {

  ArticleRepository articleRepository;

  ArticleMapper articleMapper;

  public Group from(GroupRequest groupRequest){
    List<Article> articles = new ArrayList<>();
    for(long id: groupRequest.getArticles()){
      Optional<Article> article = articleRepository.findById(id);
      if(article.isPresent()){
        articles.add(article.get());
      }else{
        log.error(String.format("Article with id: %d not found!", id));
        throw new ArticleNotFoundException(id);
      }
    }
    return Group.builder().name(groupRequest.getName()).articles(articles).build();
  }

  public GroupResponse from(Group group){
    return GroupResponse.builder().id(group.getId()).articles(group.getArticles() != null ?
        group.getArticles().stream().map(article -> articleMapper.from(article)).collect(Collectors.toList()):
        new ArrayList<>()).name(group.getName()).build();
  }
}
