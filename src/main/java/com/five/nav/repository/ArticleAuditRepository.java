package com.five.nav.repository;

import com.five.nav.audit.ArticleAudit;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleAuditRepository extends PagingAndSortingRepository<ArticleAudit, Long> {

  @Query(nativeQuery = true, value = "SELECT * FROM article_audit WHERE user_id = :userId")
  List<ArticleAudit> findAllForUser(@Param("userId") long userId);

  @Query(nativeQuery = true, value = "SELECT * FROM article_audit WHERE article_id = :articleId AND user_id = :userId")
  List<ArticleAudit> findAllForArticle(@Param("articleId") long articleId, @Param("userId") long userId);

  @Query(nativeQuery = true, value = "SELECT * FROM article_audit WHERE id = :id AND user_id = :userId")
  ArticleAudit findById(@Param("id") long id, @Param("userId") long userId);
}

