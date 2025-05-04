package com.ecommerce.products.infra.jpa.repository;

import com.ecommerce.products.infra.jpa.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}