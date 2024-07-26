package com.jisu.backend.repository;

import com.jisu.backend.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE a.board.id = :boardId ORDER BY a.createdAt DESC")
    List<Article> findTop10ByBoardIdOrderByCreatedDateDesc(@Param("boardId") Long boardId);

    @Query("SELECT a FROM Article a WHERE a.board.id = :boardId AND a.id < :articleId ORDER BY a.createdAt DESC")
    List<Article> findTop10ByBoardIdAndIdLessThanOrderByCreatedDateDesc(@Param("boardId") Long boardId, @Param("articleId") Long articleId);

    @Query("SELECT a FROM Article a WHERE a.board.id = :boardId AND a.id > :articleId ORDER BY a.createdAt DESC")
    List<Article> findTop10ByBoardIdAndIdGreaterThanOrderByCreatedDateDesc(@Param("boardId") Long boardId, @Param("articleId") Long articleId);
}
