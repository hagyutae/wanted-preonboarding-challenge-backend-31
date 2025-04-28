package com.ecommerce.product.infrastructure;

import com.ecommerce.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    boolean existsByOptionGroupProductIdAndStockGreaterThan(Long productId, int minStock);

}
