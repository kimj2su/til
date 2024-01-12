package com.example.kotlinissueservice.domain.enums

enum class IssueType {

    BUG, TASK;

    companion object {
        // of ㅁㅔ서드에서 생성자 방식으로 변경함
        // val type = IssueType.of("BUG") -> IssueType("BUG")
        operator fun invoke(type : String) = valueOf(type.uppercase())
    }
}