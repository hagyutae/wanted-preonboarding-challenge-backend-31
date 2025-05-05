package wanted.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wanted.domain.category.entity.Category;
import wanted.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
    SELECT p FROM Product p
    LEFT JOIN FETCH p.seller
    LEFT JOIN FETCH p.brand
    LEFT JOIN FETCH p.productDetail
    LEFT JOIN FETCH p.productPrice
    LEFT JOIN FETCH p.productCategories pc
    LEFT JOIN FETCH pc.category
    LEFT JOIN FETCH p.tags pt
    LEFT JOIN FETCH pt.tag
    LEFT JOIN FETCH p.optionGroups og
    LEFT JOIN FETCH p.images
    WHERE p.id = :productId
""")
    Optional<Product> findByIdWithAssociations(@Param("productId") Long productId);




    @Query("""
        SELECT DISTINCT p FROM Product p
        JOIN p.productCategories pc
        WHERE pc.category.id IN (
            SELECT pc2.category.id FROM ProductCategory pc2 WHERE pc2.product.id = :productId
        )
        AND p.id != :productId
        AND p.status = 'ACTIVE'
        ORDER BY p.createdAt DESC
    """)
    List<Product> findRelatedProducts(@Param("productId") Long productId);

    Page<Product> findByProductCategories_Category_IdIn(List<Long> categoryIds, Pageable pageable);
}
