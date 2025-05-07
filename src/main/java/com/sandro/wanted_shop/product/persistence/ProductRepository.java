package com.sandro.wanted_shop.product.persistence;

import com.sandro.wanted_shop.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, QueryDslProductRepository {

    @Query("""
            SELECT p
            FROM ProductCategory pc
                left join Product p on p.id = pc.product.id
            WHERE pc.category.id = :categoryId
            """)
    List<Product> findAllByCategoryId(Long categoryId);

    @EntityGraph(attributePaths = {"images"})
    Optional<Product> findWithImagesById(long productId);

    @EntityGraph(attributePaths = {
            "price", "detail", "categories", "categories.category"
    })
    Optional<Product> findWithDetailById(Long id);

    @EntityGraph(attributePaths = {
            "price", "detail", "brand", "seller", "reviews"
    })
    @Override
    Page<Product> findAll(Pageable pageable);
}
