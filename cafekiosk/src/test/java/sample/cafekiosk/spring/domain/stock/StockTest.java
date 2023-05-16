package sample.cafekiosk.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
    @Test
    void isQuantityLessThan() {
        // given : 선행조건 기술
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // when : 기능 수행
        boolean result = stock.isQuantityLessThan(quantity);

        // then : 결과 확인
        assertThat(result).isTrue();
    }

    @DisplayName("재고의 주어진 개수만큼 차감할 수 있다.")
    @Test
    void deductQuantity() {
        // given : 선행조건 기술
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        // when : 기능 수행
        stock.deductQuantity(quantity);

        // then : 결과 확인
        assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("재고보다 많은 수량으로 차감 시도하는 경우 예외가 발생한다.")
    @Test
    void deductQuantity2() {
        // given : 선행조건 기술
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // when : 기능 수행    // then : 결과 확인
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족합니다.");
    }

}