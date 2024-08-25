package com.jisu.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

  private final BoardRepository boardRepository;
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final ElasticSearchService elasticSearchService;
  private final ObjectMapper objectMapper;

  @Transactional
  public Article articleArticle(Long boardId, WriteArticleDto dto) throws JsonProcessingException {
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
        .viewCount(0L)
        .build();
    Article save = articleRepository.save(article);
    indexArticle(save);
    return save;
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
  public Article modifyArticle(Long boardId, Long articleId, EditArticleDto editArticleDto)
      throws JsonProcessingException {
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
    indexArticle(article);
    return article;
  }

  private boolean isCanWriteArticle() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Article artile = articleRepository.findLatestArticleByAuthorUsernameOrderByCreatedAt(
        userDetails.getUsername());
    if (artile == null || artile.getUpdatedAt() == null) {
      return true;
    }
    return isDifferenceMoreThanFiveMinutes(artile.getCreatedAt());
  }

  private boolean isCanEditArticle() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Article artile = articleRepository.findLatestArticleByAuthorUsernameOrderByUpdatedAt(
        userDetails.getUsername());
    if (artile == null || artile.getUpdatedAt() == null) {
      return true;
    }
    return isDifferenceMoreThanFiveMinutes(artile.getUpdatedAt());
  }

  private boolean isDifferenceMoreThanFiveMinutes(LocalDateTime localDateTime) {
    LocalDateTime dateAsLocalDateTime = new Date().toInstant().atZone(ZoneId.systemDefault())
        .toLocalDateTime();

    Duration duration = Duration.between(localDateTime, dateAsLocalDateTime);

    return Math.abs(duration.toMinutes()) > 5;
  }

  @Transactional
  public void deleteArticle(Long boardId, Long articleId) throws JsonProcessingException {
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

    indexArticle(article);
    article.delete();
  }

  public List<Article> searchArticle(String keyword) {
    Mono<List<Long>> articleIds = elasticSearchService.articleSearch(keyword);
    try {
      return articleRepository.findAllById(articleIds.toFuture().get());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private String indexArticle(Article article) throws JsonProcessingException {
    String articleJson = objectMapper.writeValueAsString(article);
    return elasticSearchService.indexArticleDocument(article.getId().toString(), articleJson)
        .block();
  }
}
