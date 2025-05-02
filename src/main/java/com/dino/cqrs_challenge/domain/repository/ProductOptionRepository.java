package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
