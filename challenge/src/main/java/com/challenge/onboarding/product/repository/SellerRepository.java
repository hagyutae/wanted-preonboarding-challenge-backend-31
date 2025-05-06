package com.challenge.onboarding.product.repository;

import com.challenge.onboarding.product.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
