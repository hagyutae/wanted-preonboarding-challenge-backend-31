package com.psh10066.commerce.infrastructure.dao.seller;

import com.psh10066.commerce.domain.model.seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerJpaRepository extends JpaRepository<Seller, Long> {
}
