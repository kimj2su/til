package com.jisu.backend.controller;

import com.jisu.backend.dto.EditArticleDto;
import com.jisu.backend.dto.WriteArticleDto;
import com.jisu.backend.entity.Article;
import com.jisu.backend.service.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class ArticleController {

  private final ArticleService articleService;

  @PostMapping("/{boardId}/articles")
  public ResponseEntity<Article> writeArticle(@PathVariable Long boardId,
      @RequestBody WriteArticleDto writeArticleDto) {
    return ResponseEntity.ok(articleService.articleArticle(boardId, writeArticleDto));
  }

  @GetMapping("/{boardId}/articles")
  public ResponseEntity<List<Article>> writeArticle(@PathVariable Long boardId,
      @RequestParam(required = false) Long lastId,
      @RequestParam(required = false) Long firstId) {
    if (lastId != null) {
      return ResponseEntity.ok(articleService.getOldArticle(boardId, lastId));
    }

    if (firstId != null) {
      return ResponseEntity.ok(articleService.getNewArticle(boardId, firstId));
    }
    return ResponseEntity.ok(articleService.firstGetArticle(boardId));
  }

  @PutMapping("/{boardId}/articles/{articleId}")
  public ResponseEntity<Article> modifyArticle(@PathVariable Long boardId,
      @PathVariable Long articleId,
      @RequestBody EditArticleDto editArticleDto) {
    return ResponseEntity.ok(articleService.modifyArticle(boardId, articleId, editArticleDto));
  }

  @DeleteMapping("/{boardId}/article/{articleId}")
  public ResponseEntity<Void> deleteArticle(@PathVariable Long boardId,
      @PathVariable Long articleId) {
    articleService.deleteArticle(boardId, articleId);
    return ResponseEntity.noContent().build();
  }
}
