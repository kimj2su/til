package com.example.kotlinissueservice.web

import com.example.kotlinissueservice.config.AuthUser
import com.example.kotlinissueservice.domain.enums.IssueStatus
import com.example.kotlinissueservice.model.IssueRequest
import com.example.kotlinissueservice.service.IssueService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/issues")
class IssueController (
        private val issueService: IssueService,
){

    @PostMapping
    fun create(
            authUser: AuthUser,
            @RequestBody request: IssueRequest
    ) = issueService.createIssue(authUser.userId, request)

    @GetMapping
    fun getAll(
            authUser: AuthUser,
            @RequestParam(required = false, defaultValue = "TODO") status: IssueStatus,
    ) = issueService.getAllIssues(authUser.userId, status)

    @GetMapping("/{id}")
    fun getIssue(
            authUser: AuthUser,
            @PathVariable id: Long,
    ) = issueService.getIssue(id)

    @PutMapping("/{id}")
    fun edit(
            authUser: AuthUser,
            @PathVariable id: Long,
            @RequestBody request: IssueRequest,
    ) = issueService.edit(authUser.userId, id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete (
            authUser: AuthUser,
            @PathVariable id: Long,
    ) {
        issueService.delete(id)
    }
}