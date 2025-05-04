package com.ecommerce.products.infra.jpa.repository;

import com.ecommerce.products.infra.jpa.entity.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {
}