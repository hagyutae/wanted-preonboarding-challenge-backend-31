package com.challenge.onboarding.product.repository;

import com.challenge.onboarding.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
