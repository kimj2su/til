package com.jisu.backend.repository;

import com.jisu.backend.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query("SELECT c FROM Comment c JOIN c.author u WHERE u.username = :username ORDER BY c.createdAt DESC LIMIT 1")
  Comment findLatestArticleByAuthorUsernameOrderByCreatedAt(@Param("username") String username);

  @Query("SELECT c FROM Comment c JOIN c.author u WHERE u.username = :username ORDER BY c.updatedAt DESC LIMIT 1")
  Comment findLatestArticleByAuthorUsernameOrderByUpdatedAt(@Param("username") String username);

  List<Comment> findByArticleIdOrderByCreatedAtDesc(Long articleId);
}
