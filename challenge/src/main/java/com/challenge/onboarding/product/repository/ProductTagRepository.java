package com.challenge.onboarding.product.repository;

import com.challenge.onboarding.product.domain.model.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
}
