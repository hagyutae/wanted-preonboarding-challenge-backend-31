package com.june.ecommerce.repository.product;

import com.june.ecommerce.domain.productoptiongroup.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Integer> {
    List<ProductOptionGroup> findByProductId(int productId);
}
