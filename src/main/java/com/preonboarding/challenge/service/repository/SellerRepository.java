package com.preonboarding.challenge.service.repository;

import com.preonboarding.challenge.service.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}