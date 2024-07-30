package com.jisu.backend.service;

import com.jisu.backend.dto.WriteCommentDto;
import com.jisu.backend.entity.Article;
import com.jisu.backend.entity.Board;
import com.jisu.backend.entity.Comment;
import com.jisu.backend.entity.User;
import com.jisu.backend.exception.ForbiddenException;
import com.jisu.backend.exception.RateLimitException;
import com.jisu.backend.exception.ResourceNotFoundException;
import com.jisu.backend.repository.ArticleRepository;
import com.jisu.backend.repository.BoardRepository;
import com.jisu.backend.repository.CommentRepository;
import com.jisu.backend.repository.UserRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

  private final BoardRepository boardRepository;
  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  @Transactional
  public Comment writeComment(Long boardId, Long articleId, WriteCommentDto dto) {
    if (!isCanWriteComment()) {
      throw new RateLimitException("Comment");
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User author = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new ResourceNotFoundException("author not found"));
    Board board = boardRepository.findById(boardId)
        .orElseThrow(() -> new ResourceNotFoundException("Board not found"));
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

    if (article.isDeleted()) {
      throw new ForbiddenException("Article is Deleted");
    }
    Comment comment = Comment.builder()
        .content(dto.content())
        .author(author)
        .article(article)
        .build();
    return commentRepository.save(comment);
  }

  private boolean isCanWriteComment() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Comment latestComment = commentRepository.findLatestArticleByAuthorUsernameOrderByCreatedAt(
        userDetails.getUsername());
    if (latestComment == null) {
      return true;
    }
    return isDifferenceMoreThanOneMinutes(latestComment.getCreatedAt());
  }

  private boolean isCanEditComment() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    Comment latestComment = commentRepository.findLatestArticleByAuthorUsernameOrderByUpdatedAt(
        userDetails.getUsername());
    if (latestComment == null) {
      return true;
    }
    return isDifferenceMoreThanOneMinutes(latestComment.getUpdatedAt());
  }

  private boolean isDifferenceMoreThanOneMinutes(LocalDateTime localDateTime) {
    LocalDateTime dateAsLocalDateTime = new Date().toInstant().atZone(ZoneId.systemDefault())
        .toLocalDateTime();

    Duration duration = Duration.between(localDateTime, dateAsLocalDateTime);

    return Math.abs(duration.toMinutes()) > 1;
  }
}
