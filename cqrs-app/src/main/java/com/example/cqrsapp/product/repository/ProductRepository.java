package com.example.cqrsapp.product.repository;

import com.example.cqrsapp.product.domain.Product;
import com.example.cqrsapp.product.domain.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    @EntityGraph(attributePaths = {"seller", "brand", "productDetail", "productPrice"})
    Optional<Product> findEntityGraphById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"seller", "brand", "productDetail", "productPrice"})
    @Query("select p from Product p join p.productTags tp where tp.tag in :tags")
    List<Product> findByTagIn(@Param("tags") List<Tag> tags);
}
