package com.pawn.wantedcqrs.productOptionGroup.repository;

import com.pawn.wantedcqrs.productOptionGroup.entity.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {
}
