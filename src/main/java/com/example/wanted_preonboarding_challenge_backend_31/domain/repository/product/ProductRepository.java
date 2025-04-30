package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
