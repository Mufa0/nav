package com.five.nav.repository;

import com.five.nav.audit.UserAudit;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuditRepository extends PagingAndSortingRepository<UserAudit, Long> {

  @Query(nativeQuery = true, value = "SELECT * FROM user_audit WHERE user_id = :userId")
  List<UserAudit> findAllForUser(@Param("userId") long userId);

  @Query(nativeQuery = true, value = "SELECT * FROM user_audit WHERE id = :id AND user_id = :userId")
  UserAudit findById(@Param("id") long id, @Param("userId") long userId);
}
