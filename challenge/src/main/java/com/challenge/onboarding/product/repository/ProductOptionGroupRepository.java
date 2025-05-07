package com.challenge.onboarding.product.repository;

import com.challenge.onboarding.product.domain.model.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {
}
