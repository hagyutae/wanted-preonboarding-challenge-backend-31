package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.domain.model.product.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductDetailJpaRepository extends JpaRepository<ProductDetail, Long> {

    Optional<ProductDetail> findByProductId(Long productId);
}
