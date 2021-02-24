package com.five.nav.repository;

import com.five.nav.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  @Query(nativeQuery = true,value = "SELECT * FROM _user WHERE _user.email = :email")
  Optional<User> findByEmail(@Param("email") String email);
}
