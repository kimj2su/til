package com.group.libraryapp.domain.book

import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long> {

//    fun findByName(name: String): Optional<Book>

    fun findByName(name: String): Book?
}