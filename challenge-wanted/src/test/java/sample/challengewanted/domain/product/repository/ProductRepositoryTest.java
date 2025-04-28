package sample.challengewanted.domain.product.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sample.challengewanted.api.controller.product.request.ProductCreateRequest;
import sample.challengewanted.api.controller.product.request.ProductRequest;
import sample.challengewanted.domain.brand.BrandRepository;
import sample.challengewanted.domain.product.entity.Product;
import sample.challengewanted.domain.seller.SellerRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    BrandRepository brandRepository;

    @DisplayName("상품을 조회한다.")
    @Test
    void 상품_아이디_조회_검증() {
        // given
        ProductRequest request = ProductRequest.builder()
                .id(48L)
                .build();

        // when
        Optional<Product> findProduct = repository.findById(request.getId());

        // then
        assertThat(findProduct.isPresent()).isTrue();
        assertThat(findProduct.get().getName()).isEqualTo("슈퍼 편안한 소파");
    }


}