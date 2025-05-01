package com.example.preonboarding.seller.repository;

import com.example.preonboarding.seller.domain.Sellers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Sellers,Long> {
}
