package com.five.nav.repository;

import com.five.nav.audit.ArticleAudit;
import com.five.nav.audit.UserAudit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuditRepository extends PagingAndSortingRepository<UserAudit, Long> {

}
