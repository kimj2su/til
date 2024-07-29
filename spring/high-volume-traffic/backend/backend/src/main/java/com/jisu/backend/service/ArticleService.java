package com.jisu.backend.service;

import com.jisu.backend.dto.EditArticleDto;
import com.jisu.backend.dto.WriteArticleDto;
import com.jisu.backend.entity.Article;
import com.jisu.backend.entity.Board;
import com.jisu.backend.entity.User;
import com.jisu.backend.exception.ForbiddenException;
import com.jisu.backend.exception.RateLimitException;
import com.jisu.backend.exception.ResourceNotFoundException;
import com.jisu.backend.repository.ArticleRepository;
import com.jisu.backend.repository.BoardRepository;
import com.jisu.backend.repository.UserRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    if (!isCanWriteArticle()) {
      throw new RateLimitException("5분 이내에는 작성할 수 없습니다.");
    }

    User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));
    Board board = boardRepository.findById(boardId)
        .orElseThrow(() -> new ResourceNotFoundException("게시판이 존재하지 않습니다."));
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

  @Transactional
  public Article modifyArticle(Long boardId, Long articleId, EditArticleDto editArticleDto) {
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new ResourceNotFoundException("게시글을 찾을 수 없습니다."));

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));

    if (!isCanEditArticle()) {
      throw new RateLimitException("5분 이내에는 수정할 수 없습니다.");
    }

    if (article.getAuthor() != user) {
      throw new ForbiddenException("수정할 수 없습니다.");
    }

    if (!article.getBoard().getId().equals(boardId)) {
      throw new ResourceNotFoundException("게시글을 찾을 수 없습니다.");
    }

    if (editArticleDto.title().isPresent()) {
      article.setTitle(editArticleDto.title().get());
    }

    if (editArticleDto.content().isPresent()) {
      article.setContent(editArticleDto.content().get());
    }

    return article;
  }

  private boolean isCanWriteArticle() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Article artile = articleRepository.findLatestArticleByAuthorUsernameOrderByCreatedAt(
        userDetails.getUsername());
    return isDifferenceMoreThanFiveMinutes(artile.getCreatedAt());
  }

  private boolean isCanEditArticle() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Article artile = articleRepository.findLatestArticleByAuthorUsernameOrderByUpdatedAt(
        userDetails.getUsername());
    return isDifferenceMoreThanFiveMinutes(artile.getUpdatedAt());
  }

  private boolean isDifferenceMoreThanFiveMinutes(LocalDateTime localDateTime) {
    LocalDateTime dateAsLocalDateTime = new Date().toInstant().atZone(ZoneId.systemDefault())
        .toLocalDateTime();

    Duration duration = Duration.between(localDateTime, dateAsLocalDateTime);

    return Math.abs(duration.toMinutes()) > 5;
  }

  @Transactional
  public void deleteArticle(Long boardId, Long articleId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new ResourceNotFoundException("게시글을 찾을 수 없습니다."));

    if (article.getAuthor() != user) {
      throw new ForbiddenException("수정할 수 없습니다.");
    }

    if (!article.getBoard().getId().equals(boardId)) {
      throw new ResourceNotFoundException("게시글을 찾을 수 없습니다.");
    }

    if (!isCanEditArticle()) {
      throw new RateLimitException("5분 이내에는 수정할 수 없습니다.");
    }

    article.delete();
  }
}
