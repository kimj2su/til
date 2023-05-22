package sample.cafekiosk.spring.api.service.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.domain.product.ProductRepository;

import static org.assertj.core.api.Assertions.assertThat;

class ProductNumberFactoryTest extends IntegrationTestSupport {

    @Autowired
    private ProductNumberFactory productNumberFactory;
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("신규 상품을 등록할때 상품번호를 생성한다.")
    @Test
    void createNextProductNumber() {
        // given : 선행조건 기술
        // when : 기능 수행
        String nextProductNumber = productNumberFactory.createNextProductNumber();

        // then : 결과 확인
        assertThat(nextProductNumber).isEqualTo("001");
    }

}