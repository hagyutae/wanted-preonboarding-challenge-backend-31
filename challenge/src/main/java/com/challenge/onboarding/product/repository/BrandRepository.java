package com.challenge.onboarding.product.repository;

import com.challenge.onboarding.product.domain.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
