package com.group.librayapp

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class JunitTest {

    // 자바에서 스태틱메서드
    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            println("모든 테스트 시작 전")
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            println("모든 테스트 시작 후")
        }
    }
    @BeforeEach
    fun beforeEach() {
        println("각 테스트 전에 실행")
    }

    @AfterEach
    fun afterEach() {
        println("각 테스트 후에 실행")
    }

    @Test
    fun test1() {
        println("테스트 1")
    }

    @Test
    fun test2() {
        println("테스트 2")
    }
}