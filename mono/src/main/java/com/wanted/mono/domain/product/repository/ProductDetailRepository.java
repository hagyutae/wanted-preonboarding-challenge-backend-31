package com.wanted.mono.domain.product.repository;

import com.wanted.mono.domain.product.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
}
