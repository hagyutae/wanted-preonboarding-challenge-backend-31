package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    void deleteAllByProductId(Long productId);

    @Query("select pc from ProductCategory pc "
            + "join fetch pc.category c "
            + "join fetch c.parentCategory "
            + "where pc.product.id = :productId")
    List<ProductCategory> findAllByProductId(@Param("productId") Long productId);
}
