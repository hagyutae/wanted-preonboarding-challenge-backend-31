package com.june.ecommerce.repository.seller;

import com.june.ecommerce.domain.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
}
