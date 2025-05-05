package com.example.cqrsapp.seller.repository;

import com.example.cqrsapp.seller.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
