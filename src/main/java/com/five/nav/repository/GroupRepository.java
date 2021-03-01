package com.five.nav.repository;

import com.five.nav.domain.Group;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends PagingAndSortingRepository<Group, Long> {

  @Query(nativeQuery = true, value = "SELECT * FROM _group WHERE _group.user_id = :userId")
  List<Group> findGroupsForUser(@Param("userId") long userId);

  @Query(nativeQuery = true, value = "SELECT * FROM _group WHERE _group.id = :id AND _group"
      + ".user_id = :userId")
  Optional<Group> findGroupForUser(@Param("id") long id, @Param("userId") long userId);
}
