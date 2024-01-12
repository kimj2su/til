package com.example.kotlinissueservice.domain

import com.example.kotlinissueservice.domain.enums.IssuePriority
import com.example.kotlinissueservice.domain.enums.IssueStatus
import com.example.kotlinissueservice.domain.enums.IssueType
import jakarta.persistence.*

@Entity
@Table
class Issue (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column
        var userId: Long,

        @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        val comments : MutableList<Comment> = mutableListOf(),

        @Column
        var summary: String,

        @Column
        var description: String,

        @Column
        @Enumerated(EnumType.STRING)
        var type: IssueType,

        @Column
        @Enumerated(EnumType.STRING)
        var priority: IssuePriority,

        @Column
        @Enumerated(EnumType.STRING)
        var status: IssueStatus,

        ) : BaseEntity()