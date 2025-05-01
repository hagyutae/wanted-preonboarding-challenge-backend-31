package com.wanted.mono.domain.product.repository;

import com.wanted.mono.domain.product.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
}
