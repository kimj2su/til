package com.example.kotlinissueservice.web

import com.example.kotlinissueservice.config.AuthUser
import com.example.kotlinissueservice.model.CommentRequest
import com.example.kotlinissueservice.model.CommentResponse
import com.example.kotlinissueservice.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/issues/{issueId}/comments")
class CommentController(
        private val commentService: CommentService,
) {

    @PostMapping
    fun create(
            authUser: AuthUser,
            @PathVariable issueId: Long,
            @RequestBody request: CommentRequest,
    ): CommentResponse {
        return commentService.create(issueId, authUser.userId, authUser.username, request)
    }

    @PutMapping("/{id}")
    fun edit(
            authUser: AuthUser,
            @PathVariable issueId: Long,
            @RequestBody request: CommentRequest,
    ) = commentService.edit(issueId, authUser.userId, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
            authUser: AuthUser,
            @PathVariable issueId: Long,
            @PathVariable id: Long,
    ) {
        commentService.delete(issueId, id, authUser.userId)
    }
}