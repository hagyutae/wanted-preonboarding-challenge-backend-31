package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

    ProductPrice findByProductId(Long productId);

    List<ProductPrice> findAllByProductIdIn(Collection<Long> productIds);
}
