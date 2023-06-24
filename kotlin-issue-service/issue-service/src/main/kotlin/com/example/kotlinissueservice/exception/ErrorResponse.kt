package com.example.kotlinissueservice.exception

data class ErrorResponse(
        val code: Int,
        val message: String,
)