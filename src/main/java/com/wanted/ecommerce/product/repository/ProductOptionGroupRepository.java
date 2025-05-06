package com.wanted.ecommerce.product.repository;

import com.wanted.ecommerce.product.domain.ProductOptionGroup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {

    void deleteByProductId(Long productId);

    Optional<ProductOptionGroup> findByIdAndProductId(Long id, Long productId);
}
