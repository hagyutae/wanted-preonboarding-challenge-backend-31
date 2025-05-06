package minseok.cqrschallenge.product.repository;

import java.util.List;
import minseok.cqrschallenge.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    boolean existsBySlug(String slug);

    @Query("SELECT DISTINCT p FROM Product p " +
        "JOIN p.categories pc " +
        "WHERE pc.category.id IN :categoryIds " +
        "AND p.status <> 'DELETED'")
    Page<Product> findByCategoryIds(@Param("categoryIds") List<Long> categoryIds,
        Pageable pageable);

    @Query("SELECT p FROM Product p " +
        "LEFT JOIN FETCH p.seller " +
        "LEFT JOIN FETCH p.brand " +
        "LEFT JOIN FETCH p.categories pc " +
        "LEFT JOIN FETCH pc.category " +
        "LEFT JOIN FETCH p.images " +
        "LEFT JOIN FETCH p.optionGroups og " +
        "LEFT JOIN FETCH og.options " +
        "LEFT JOIN FETCH p.tags pt " +
        "LEFT JOIN FETCH pt.tag " +
        "WHERE p.id = :id AND p.status <> 'DELETED'")
    Product findByIdWithAllDetails(@Param("id") Long id);

    @Query("SELECT p FROM Product p " +
        "WHERE p.status = 'ACTIVE' " +
        "ORDER BY p.createdAt DESC")
    List<Product> findNewProducts(Pageable pageable);

    @Query("SELECT p FROM Product p " +
        "LEFT JOIN p.reviews r " +
        "WHERE p.status = 'ACTIVE' " +
        "GROUP BY p.id " +
        "ORDER BY AVG(r.rating) DESC, COUNT(r) DESC")
    List<Product> findPopularProducts(Pageable pageable);

}
