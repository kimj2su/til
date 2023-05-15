package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

class OrderTest {

    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice() {
        // given : 선행조건 기술
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        // when : 기능 수행
        Order order = Order.create(products, LocalDateTime.now());

        // then : 결과 확인
        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }

    @DisplayName("주문 생성 시 주문 상태는 INIT이다.")
    @Test
    void init() {
        // given : 선행조건 기술
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        // when : 기능 수행
        Order order = Order.create(products, LocalDateTime.now());

        // then : 결과 확인
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.INIT);
    }

    @DisplayName("주문 생성 시 주문 등록시간을 기록한다.")
    @Test
    void registeredDateTime() {
        // given : 선행조건 기술
        LocalDateTime registeredDateTime = LocalDateTime.now();
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        // when : 기능 수행
        Order order = Order.create(products, registeredDateTime);

        // then : 결과 확인
        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .type(HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }

}