package com.wanted.ecommerce.seller.repository;

import com.wanted.ecommerce.seller.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {

}
