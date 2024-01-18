package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User

data class UserResponse(
    val id: Long,
    val name: String,
    val age: Int?
) {

    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                user.id!!,
                user.name,
                user.age
            )
        }
    }

//    constructor(user: User) : this(
//        user.id!!,
//        user.name,
//        user.age
//    )
}
