package sample.challengewanted.domain.product.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import sample.challengewanted.domain.product.repository.ProductRepository;
import sample.challengewanted.dto.product.ProductPageResponse;
import sample.challengewanted.dto.product.ProductSearchCondition;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductRepositoryImplTest {

    @Autowired
    ProductRepository productRepository;

    @PersistenceContext
    EntityManager entityManager;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @DisplayName("상품을 응답모델에 맞게 조회하는지 확인한다.")
    @Test
    void 페이지_쿼리_응답_모델을_확인하다() {
        // given
        ProductSearchCondition condition = ProductSearchCondition.builder()
                .status("ACTIVE")
                .minPrice(1000)
                .maxPrice(599000)
                .build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<ProductPageResponse> result = productRepository.searchProducts(condition, pageable);

        // then
        assertThat(result.getContent()).allSatisfy(product -> assertThat(product.getStatus()).isEqualTo("ACTIVE"));
        assertThat(result.getTotalElements()).isEqualTo(8);
    }

}