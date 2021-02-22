package com.five.nav.repository;

import com.five.nav.audit.ArticleAudit;
import com.five.nav.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleAuditRepository extends PagingAndSortingRepository<ArticleAudit, Long> {

}
