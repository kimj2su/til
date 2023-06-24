package com.example.kotlinissueservice.domain

import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY

@Entity
@Table
class Comment(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "issue_id")
        val issue: Issue,

        @Column
        val userId: Long,

        @Column
        val username: String,

        @Column
        var body: String,

    ) : BaseEntity()