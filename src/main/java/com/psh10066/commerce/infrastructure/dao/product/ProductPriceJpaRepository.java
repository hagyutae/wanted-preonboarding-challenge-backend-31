package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.domain.model.product.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductPriceJpaRepository extends JpaRepository<ProductPrice, Long> {

    Optional<ProductPrice> findByProductId(Long productId);
}
