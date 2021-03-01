package com.five.nav.repository;

import com.five.nav.domain.Article;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

  @Query(nativeQuery = true,value = "SELECT article.* FROM article article WHERE status = 'ACTIVE' "
      + "ORDER BY ( SELECT audit.timestamp FROM article_audit audit WHERE article.id = audit.article_id AND action = 'CREATE') DESC")
  List<Article> findAll();

  @Query(nativeQuery = true, value="SELECT * FROM article WHERE article.status='ACTIVE' AND "
      + "article.author=:author "
      + "ORDER BY ( SELECT audit.timestamp FROM article_audit audit WHERE article.id = audit.article_id AND action = 'CREATE') DESC")
  List<Article> findAllByAuthor(@Param("author") long authorId);

}
