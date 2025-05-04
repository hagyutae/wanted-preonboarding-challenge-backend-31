package com.psh10066.commerce.infrastructure.dao.product;

import com.psh10066.commerce.domain.model.product.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionGroupJpaRepository extends JpaRepository<ProductOptionGroup, Long> {

    List<ProductOptionGroup> findAllByProductId(Long productId);
}
