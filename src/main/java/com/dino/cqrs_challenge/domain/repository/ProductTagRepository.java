package com.dino.cqrs_challenge.domain.repository;

import com.dino.cqrs_challenge.domain.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
}
