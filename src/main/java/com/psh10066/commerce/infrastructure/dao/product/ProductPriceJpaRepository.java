package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.domain.model.product.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceJpaRepository extends JpaRepository<ProductPrice, Long> {
}
