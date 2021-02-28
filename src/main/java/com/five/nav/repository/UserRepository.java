package com.five.nav.repository;

import com.five.nav.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  @Query(nativeQuery = true,value = "SELECT _user.* FROM _user _user WHERE status = 'ACTIVE' "
      + "ORDER BY ( SELECT audit.timestamp FROM user_audit audit WHERE _user.id = audit"
      + ".user_id AND action = 'CREATE') DESC")
  List<User> findAll();

  @Query(nativeQuery = true,value = "SELECT * FROM _user WHERE _user.email = :email")
  Optional<User> findByEmail(@Param("email") String email);
}
