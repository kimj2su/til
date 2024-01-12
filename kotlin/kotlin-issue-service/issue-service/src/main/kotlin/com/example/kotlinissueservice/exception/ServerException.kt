package com.example.kotlinissueservice.exception

import java.lang.RuntimeException

sealed class ServerException (
        val code: Int,
        override val message: String,
) : RuntimeException(message)

data class NotFoundException(
        override val message: String,
) : ServerException(404, message)

data class UnauthorizedException(
        override val message: String,
) : ServerException(401, "인증정보가 잘못되었습니다.")

data class BadRequestException(
        override val message: String,
) : ServerException(400, message)