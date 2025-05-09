package com.wanted.mono.domain.seller.repository;

import com.wanted.mono.domain.seller.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
