package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductImage;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    void deleteAllByProductId(Long productId);

    @EntityGraph(attributePaths = "productOption")
    List<ProductImage> findAllByProductId(Long productId);
}
