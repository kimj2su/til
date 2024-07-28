package com.jisu.backend.repository;

import com.jisu.backend.entity.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  @Query("SELECT a FROM Article a WHERE a.board.id = :boardId ORDER BY a.createdAt DESC LIMIT 10")
  List<Article> findTop10ByBoardIdOrderByCreatedDateDesc(@Param("boardId") Long boardId);

  @Query("SELECT a FROM Article a WHERE a.board.id = :boardId AND a.id < :articleId ORDER BY a.createdAt DESC LIMIT 10")
  List<Article> findTop10ByBoardIdAndIdLessThanOrderByCreatedDateDesc(
      @Param("boardId") Long boardId, @Param("articleId") Long articleId);

  @Query("SELECT a FROM Article a WHERE a.board.id = :boardId AND a.id > :articleId ORDER BY a.createdAt DESC LIMIT 10")
  List<Article> findTop10ByBoardIdAndIdGreaterThanOrderByCreatedDateDesc(
      @Param("boardId") Long boardId, @Param("articleId") Long articleId);

  @Query("SELECT a FROM Article a JOIN a.author u WHERE u.username = :username ORDER BY a.createdAt DESC LIMIT 1")
  Article findLatestArticleByAuthorUsernameOrderByCreatedAt(@Param("username") String username);

  @Query("SELECT a FROM Article a JOIN a.author u WHERE u.username = :username ORDER BY a.updatedAt DESC LIMIT 1")
  Article findLatestArticleByAuthorUsernameOrderByUpdatedAt(@Param("username") String username);
}
