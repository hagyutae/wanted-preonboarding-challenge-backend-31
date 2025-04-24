package com.ecommerce.seller.infrastructure;

import com.ecommerce.seller.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
