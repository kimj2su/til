package com.example.tddpractice.product;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void 상품조회() {
        // given : 선행조건 기술 - 상품 등록
        productService.addProduct(ProductSteps.상품등록요청_생성());
        final long productId = 1L;

        // when : 기능 수행 - 상품을 조회
        final GetProductResponse response = productService.getProduct(productId);

        // then : 결과 확인 - 상품의 응답을 검증
        assertThat(response).isNotNull();
    }

}
