package com.wanted.mono.domain.product.repository;

import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.domain.product.entity.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {
    List<ProductOptionGroup> findByProductAndName(Product product, String name);

    Optional<ProductOptionGroup> findByIdAndProductId(Long optionGroupId, Long productId);
}
