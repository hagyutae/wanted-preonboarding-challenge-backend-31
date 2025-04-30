package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductOption;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    Optional<ProductOption> findByIdAndProductOptionGroup_Product_Id(Long id, Long productId);

    @Query("select po.productOptionGroup.id from ProductOption po where po.id = :id")
    Long findProductOptionGroupIdById(@Param("id") Long id);
}
