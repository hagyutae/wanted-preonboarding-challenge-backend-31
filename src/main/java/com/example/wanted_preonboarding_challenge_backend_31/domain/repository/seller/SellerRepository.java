package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.seller;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
