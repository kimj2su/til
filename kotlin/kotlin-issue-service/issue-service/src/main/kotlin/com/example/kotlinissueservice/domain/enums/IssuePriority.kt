package com.example.kotlinissueservice.domain.enums

enum class IssuePriority {

    LOW, MEDIUM, HIGH;

    companion object {
        // of ㅁㅔ서드에서 생성자 방식으로 변경함
        // val type = IssueType.of("BUG") -> IssueType("BUG")
        operator fun invoke(priority : String) = valueOf(priority.uppercase())
    }

}
