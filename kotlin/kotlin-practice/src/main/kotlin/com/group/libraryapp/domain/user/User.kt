package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class User(
    @Column(nullable = false)
    var name: String, // 불변이므로 val로 선언

    val age: Int?, // 불변이므로 val로 선언;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long? = null, // 불변이므로 val로 선언, null이 될 수 있으므로 ?로 선언

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLoanHistories: MutableList<UserLoanHistory> = mutableListOf(), // 불변이므로 val로 선언
) {

    // init 블록은 생성자가 호출되기 전에 실행되므로, 생성자에서 검증할 수 없는 프로퍼티의 유효성을 검증할 수 있다.
    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("name must not be blank")
        }
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun loanBook(book: Book) {
        this.userLoanHistories.add(UserLoanHistory(user = this, bookName = book.name, isReturn = false))
    }

    fun returnBook(book: String) {
        // first()는 조건에 맞는 첫번째 요소를 반환한다. 만약 조건에 맞는 요소가 없다면 NoSuchElementException이 발생한다.
        this.userLoanHistories.first() { it.bookName == book }.doReturn()
    }
}