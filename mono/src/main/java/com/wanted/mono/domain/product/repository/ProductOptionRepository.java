package com.wanted.mono.domain.product.repository;

import com.wanted.mono.domain.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    void deleteProductOptionById(Long productOptionId);
}
