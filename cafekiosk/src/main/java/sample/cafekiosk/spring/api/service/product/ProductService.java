package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;

import java.util.List;

import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

/**
 *  readOnly = true : 읽기 전용
 *  CRUD 에서 CUD 동작 x / only Read
 *  JPA : CUD 스냅샷 저장, 변경감지 X (성능 향상 효과가 있다.)
 *
 *  CQRS - Command / Query 분리
 *
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        // nextProductNumber
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(forDisplay());
        return products.stream().map(ProductResponse::of).toList();
    }

    private String createNextProductNumber() {
        // productNumber
        // 001 002 003 004
        // DB에서 마지막 저장된 Product의 상품번호를 읽어와서 + 1
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }
//        int nextProductNumber = Integer.parseInt(latestProductNumber) + 1;
        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        // 9 -> 009, 10 -> 010
        return String.format("%03d", nextProductNumberInt);

    }
}
