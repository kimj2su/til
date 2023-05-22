package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType() {
        // given : 선행조건 기술
        ProductType bottle = ProductType.BOTTLE;

        // when : 기능 수행
        boolean result = ProductType.containsStockType(bottle);

        // then : 결과 확인
        assertThat(result).isTrue();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType2() {
        // given : 선행조건 기술
        ProductType bakery = ProductType.BAKERY;

        // when : 기능 수행
        boolean result = ProductType.containsStockType(bakery);

        // then : 결과 확인
        assertThat(result).isTrue();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @CsvSource({"HANDMADE, false", "BOTTLE, true", "BAKERY, true"})
    @ParameterizedTest
    void containsStockType3(ProductType productType, boolean expected) {
        //when
        boolean result = ProductType.containsStockType(productType);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideProductTypeForCheckingStockType() {
        return Stream.of(
                Arguments.of(ProductType.HANDMADE, false),
                Arguments.of(ProductType.BOTTLE, true),
                Arguments.of(ProductType.BAKERY, true)
        );
    }
    @DisplayName("상품 타입이 재고 고나련 타입인지를 체크한다.")
    @MethodSource("provideProductTypeForCheckingStockType")
    @ParameterizedTest
    void containsStockType4(ProductType productType, boolean expected) {
        //when
        boolean result = ProductType.containsStockType(productType);

        //then
        assertThat(result).isEqualTo(expected);
    }

}