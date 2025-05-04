package com.ecommerce.products.infra.jpa.repository;

import com.ecommerce.products.infra.jpa.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}