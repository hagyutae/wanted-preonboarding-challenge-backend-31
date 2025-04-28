package com.mkhwang.wantedcqrs.product.infra;

import com.mkhwang.wantedcqrs.product.domain.ProductOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductOptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {
  Optional<ProductOptionGroup> findProductOptionGroupByProductIdAndId(Long productId, Long optionGroupId);
}
