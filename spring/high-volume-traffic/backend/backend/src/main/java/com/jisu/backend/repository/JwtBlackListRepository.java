package com.jisu.backend.repository;

import com.jisu.backend.entity.JwtBlackList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtBlackListRepository extends JpaRepository<JwtBlackList, Long> {

  Optional<JwtBlackList> findByToken(String token);

  // findTop 1 , SELECT * FROM jwt_black_list WHERE username = ? ORDER BY expiration_time DESC LIMIT 1
  Optional<JwtBlackList> findTop1ByUsernameOrderByExpirationTimeDesc(String username);
}
