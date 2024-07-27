package com.jisu.backend.service;

import com.jisu.backend.dto.WriteArticleDto;
import com.jisu.backend.entity.Article;
import com.jisu.backend.entity.Board;
import com.jisu.backend.entity.User;
import com.jisu.backend.exception.ResourceNotFoundException;
import com.jisu.backend.repository.ArticleRepository;
import com.jisu.backend.repository.BoardRepository;
import com.jisu.backend.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

  private final BoardRepository boardRepository;
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;

  @Transactional
  public Article articleArticle(Long boardId, WriteArticleDto dto) {
    Board board = boardRepository.findById(boardId)
        .orElseThrow(() -> new ResourceNotFoundException("게시판이 존재하지 않습니다."));
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));
    Article article = Article.builder()
        .author(user)
        .board(board)
        .title(dto.title())
        .content(dto.content())
        .build();
    return articleRepository.save(article);
  }

  public List<Article> firstGetArticle(Long boardId) {
    return articleRepository.findTop10ByBoardIdOrderByCreatedDateDesc(boardId);
  }

  public List<Article> getOldArticle(Long boardId, Long articleId) {
    return articleRepository.findTop10ByBoardIdAndIdLessThanOrderByCreatedDateDesc(boardId,
        articleId);
  }

  public List<Article> getNewArticle(Long boardId, Long articleId) {
    return articleRepository.findTop10ByBoardIdAndIdGreaterThanOrderByCreatedDateDesc(boardId,
        articleId);
  }
}
