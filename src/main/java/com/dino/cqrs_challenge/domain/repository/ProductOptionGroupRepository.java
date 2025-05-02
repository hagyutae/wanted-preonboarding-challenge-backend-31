package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {
}
