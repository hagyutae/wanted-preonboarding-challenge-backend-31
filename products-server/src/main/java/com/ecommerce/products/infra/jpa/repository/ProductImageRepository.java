package com.ecommerce.products.infra.jpa.repository;

import com.ecommerce.products.infra.jpa.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}