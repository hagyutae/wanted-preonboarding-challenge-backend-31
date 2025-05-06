package com.challenge.onboarding.product.repository;

import com.challenge.onboarding.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
