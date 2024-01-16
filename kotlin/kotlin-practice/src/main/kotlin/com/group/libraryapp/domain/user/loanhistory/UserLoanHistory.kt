package com.group.libraryapp.domain.user.loanhistory


import com.group.libraryapp.domain.user.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class UserLoanHistory(
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User, // 불변이므로 val로 선언

    val bookName : String, // 불변이므로 val로 선언

    var isReturn : Boolean, // 변경이므로 var로 선언

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null, // 불변이므로 val로 선언, null이 될 수 있으므로 ?로 선언
) {

    fun doReturn() {
        this.isReturn = true
    }

}