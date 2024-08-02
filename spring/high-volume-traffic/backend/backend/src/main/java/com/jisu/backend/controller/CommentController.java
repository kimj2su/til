package com.jisu.backend.controller;

import com.jisu.backend.dto.WriteCommentDto;
import com.jisu.backend.entity.Comment;
import com.jisu.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards/{boardId}/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<Comment> writeComment(@PathVariable Long boardId,
      @PathVariable Long articleId,
      @RequestBody WriteCommentDto writeCommentDto) {
    return ResponseEntity.ok(commentService.writeComment(boardId, articleId, writeCommentDto));
  }

  @PutMapping("/{commentId}")
  public ResponseEntity<Comment> editComment(@PathVariable Long boardId,
      @PathVariable Long articleId,
      @PathVariable Long commentId,
      @RequestBody WriteCommentDto editCommentDto
  ) {
    return ResponseEntity.ok(
        commentService.editComment(boardId, articleId, commentId, editCommentDto));
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<Comment> deleteComment(@PathVariable Long boardId,
      @PathVariable Long articleId,
      @PathVariable Long commentId
  ) {
    return ResponseEntity.ok(commentService.deleteComment(boardId, articleId, commentId));
  }

}
