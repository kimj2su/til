package com.group.libraryapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// 코틀리은 탑 라인에 여러 클래스, 함수를 만들 수 있다. 함수를 만드는 경우 static 함수 처럼 사용할 수 있다.
@SpringBootApplication
class LibraryApplication
fun main(args: Array<String>) {
//        SpringApplication.run(LibraryApplication::class.java, *args)
    runApplication<LibraryApplication>(*args) // 코틀린 확장 함수
}
