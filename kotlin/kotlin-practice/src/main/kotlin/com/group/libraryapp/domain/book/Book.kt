package com.group.libraryapp.domain.book

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Book(
    val name: String,

    val type: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null, // val 불면
) {
    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("name must not be blank")
        }
    }

    // 정적 팩토리 메소드, 코틀린에서는 companion object을 사용하여 정적 메소드를 만들 수 있다.
    // companion object를 가장 아래 작성하는 것이 컨벤션이다.
    companion object {
        fun fixture(
            name: String = "책 이름",
            type: String = "COMPUTER",
            id: Long? = null,
        ): Book {
            return Book(
                name = name,
                type = type,
                id = id
            )
        }
    }
}