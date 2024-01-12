package com.group.libraryapp.domain.book

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Book(
    val name: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null, // val 불면
) {
    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("name must not be blank")
        }
    }
}