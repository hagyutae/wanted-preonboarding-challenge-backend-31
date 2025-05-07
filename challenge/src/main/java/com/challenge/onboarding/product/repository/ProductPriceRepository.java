package com.challenge.onboarding.product.repository;

import com.challenge.onboarding.product.domain.model.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
}
