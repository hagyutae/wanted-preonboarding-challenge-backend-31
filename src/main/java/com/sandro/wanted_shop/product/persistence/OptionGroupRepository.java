package com.sandro.wanted_shop.product.persistence;

import com.sandro.wanted_shop.product.entity.relation.ProductOptionGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionGroupRepository extends JpaRepository<ProductOptionGroup, Long> {
    Optional<ProductOptionGroup> findByProduct_idAndId(Long productId, Long id);

    @EntityGraph(attributePaths = {"options"})
    Optional<ProductOptionGroup> findWithOptionsByProduct_idAndId(Long productId, Long id);
}
