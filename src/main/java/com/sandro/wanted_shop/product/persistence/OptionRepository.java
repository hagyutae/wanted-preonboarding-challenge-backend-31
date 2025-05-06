package com.sandro.wanted_shop.product.persistence;

import com.sandro.wanted_shop.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<ProductOption, Long> {
    void deleteByOptionGroup_product_idAndId(Long productId, Long optionId);

    Optional<ProductOption> findByOptionGroup_product_idAndId(Long productId, Long optionId);
}
