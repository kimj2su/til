package com.example.kotlinissueservice.model

import com.example.kotlinissueservice.domain.Comment
import com.example.kotlinissueservice.domain.Issue
import com.example.kotlinissueservice.domain.enums.IssuePriority
import com.example.kotlinissueservice.domain.enums.IssueStatus
import com.example.kotlinissueservice.domain.enums.IssueType
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime


data class IssueRequest(
        val summary: String,
        val description: String,
        val type: IssueType,
        val priority: IssuePriority,
        val status: IssueStatus,
)

data class IssueResponse(
        val id : Long,
        val comments : List<CommentResponse> = emptyList(),
        val summary: String,
        val description: String,
        val userId : Long,
        val type: IssueType,
        val priority: IssuePriority,
        val status: IssueStatus,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        val createdAt: LocalDateTime?,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        val updatedAt: LocalDateTime?,
) {
//        constructor(issue: com.example.kotlinissueservice.domain.Issue) : this(
//                id = issue.id!!,
//                summary = issue.summary,
//                description = issue.description,
//                userId = issue.userId,
//                type = issue.type,
//                priority = issue.priority,
//                status = issue.status,
//                createdAt = issue.createdAt,
//                updatedAt = issue.updatedAt,
//        )

    companion object {
        operator fun invoke(issue: Issue) =
            with(issue) {
                    IssueResponse(
                            id = id!!, // id = issue.id!! 이지만 with 로 생략가능함
                            comments = comments.sortedByDescending(Comment::id).map(Comment::toResponse),
                            summary = summary,
                            description = description,
                            userId = userId,
                            type = type,
                            priority = priority,
                            status = status,
                            createdAt = createdAt,
                            updatedAt = updatedAt,
                    )
            }
    }
}