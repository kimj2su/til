package com.example.kotlinissueservice.service

import com.example.kotlinissueservice.domain.Issue
import com.example.kotlinissueservice.domain.IssueRepository
import com.example.kotlinissueservice.domain.enums.IssueStatus
import com.example.kotlinissueservice.exception.NotFoundException
import com.example.kotlinissueservice.model.IssueRequest
import com.example.kotlinissueservice.model.IssueResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IssueService (
        private val issueRepository: IssueRepository,
){

    @Transactional
    fun createIssue(userId : Long, request: IssueRequest): IssueResponse {

        val issue = Issue(
                summary = request.summary,
                description = request.description,
                userId = userId,
                type = request.type,
                priority = request.priority,
                status = request.status,
        )

        return IssueResponse(issueRepository.save(issue))
    }

    @Transactional(readOnly = true)
    fun getAllIssues(userId: Long, status: IssueStatus) =
            issueRepository.findAllByStatusOrderByCreatedAtDesc(status)
                    ?.map { IssueResponse(it) }

    @Transactional(readOnly = true)
    fun getIssue(id: Long): IssueResponse {
        val issue = issueRepository.findByIdOrNull(id)
                ?: throw NotFoundException("해당 이슈가 존재하지 않습니다.")
        return IssueResponse(issue)
    }

    @Transactional
    fun edit(userId: Long, id: Long, request: IssueRequest): IssueResponse {
        val issue : Issue = (issueRepository.findByIdOrNull(id)
                ?: throw NotFoundException("해당 이슈가 존재하지 않습니다."))

        return with(issue) {
            summary = request.summary
            description = request.description
            this.userId = userId
            type = request.type
            priority = request.priority
            status = request.status
            IssueResponse(issueRepository.save(this))
        }
    }

    fun delete(id: Long) {
        issueRepository.deleteById(id)
    }
}