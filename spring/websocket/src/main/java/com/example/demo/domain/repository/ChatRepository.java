package com.example.demo.domain.repository;


import com.example.demo.domain.repository.entity.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

  List<Chat> findTop10BySenderOrReceiverOrderByTIDDesc(String sender, String receover);

  // @Query("SELECT c FROM chat AS c WHERE c.sender = :sender OR c.receiver = :receiver ORDER BY c.t_id DESC LIMIT 10")
  // List<Chat> frinTop10Chrts(@Param("sender") String sender, @Param("receiver") String receiver);
}
