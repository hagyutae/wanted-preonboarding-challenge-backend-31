package com.challenge.onboarding.product.repository;

import com.challenge.onboarding.product.domain.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
