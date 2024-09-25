package com.example.demo.domain.repository;

import com.example.demo.domain.repository.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

  Optional<User> findByName(String name);

  boolean existsByName(String name);

  //  @Query("SELECT u.name FROM User AS u WHERE LOCATE(LOWER(:pattern), LOWER(u.name)) > 0 AND u.name != :user")
//  List<String> findNameByNameMatch(@Param("pattern") String pattern, @Param("user") String user);
  @Query("{ 'name': { $regex: ?0, $options: 'i' }, 'name': { $ne: ?1 } }")
  List<String> findNameByNameMatch(String pattern, String user);
} 
