package com.group.librayapp.calculator

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class JunitCalculatorTest {

    @Test
    fun addTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.add(3)

        // then
        Assertions.assertThat(calculator.number).isEqualTo(8)
    }

    @Test
    fun minusTest(){
        // given
        val calculator = Calculator(5)

        // when
        calculator.minus(3)

        // then
        Assertions.assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun multiplyTest(){
        // given
        val calculator = Calculator(5)

        // when
        calculator.multiply(3)

        // then
        Assertions.assertThat(calculator.number).isEqualTo(15)
    }

    @Test
    fun divideTest(){
        // given
        val calculator = Calculator(5)

        // when
        calculator.divide(2)

        // then
        Assertions.assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun divideExceptionTest(){
        // given
        val calculator = Calculator(5)

        // when & then
        val message = assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }.message

        Assertions.assertThat(message).isEqualTo("Cannot divide by 0")

        assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }.apply {
            Assertions.assertThat(message).isEqualTo("Cannot divide by 0")
        }

        Assertions.assertThatThrownBy { calculator.divide(0) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("Cannot divide by 0")
    }
}