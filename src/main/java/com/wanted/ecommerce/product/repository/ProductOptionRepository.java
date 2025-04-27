package com.wanted.ecommerce.product.repository;

import com.wanted.ecommerce.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    Boolean existsByOptionGroupProductIdAndStockGreaterThan(Long productId, int stock);
}
