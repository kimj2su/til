package com.jisu.backend.controller;

import com.jisu.backend.dto.WriteArticleDto;
import com.jisu.backend.entity.Article;
import com.jisu.backend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class ArticleController {

    private final AuthenticationManager authenticationManager;
    private final ArticleService articleService;

    @PostMapping("/{boardId}/articles")
    public ResponseEntity<Article> writeArticle(@PathVariable Long boardId, @RequestBody WriteArticleDto writeArticleDto) {
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
}
