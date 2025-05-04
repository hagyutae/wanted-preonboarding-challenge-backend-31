package com.ecommerce.products.infra.jpa.repository;

import com.ecommerce.products.infra.jpa.entity.Product;
import com.ecommerce.products.infra.jpa.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    // 카테고리 ID로 상품 조회
    Page<Product> findByCategoriesId(Long categoryId, Pageable pageable);

    // 여러 카테고리 ID로 상품 조회 (하위 카테고리 포함)
    Page<Product> findByCategoriesIdIn(List<Long> categoryIds, Pageable pageable);

    // 모든 필터링 조건을 specification으로 대체할 수 있음
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    // 상태별 상품 조회 (생성일 기준 정렬)
    List<Product> findTop5ByStatusOrderByCreatedAtDesc(ProductStatus status);

    // 인기 상품 조회 (리뷰 평점 기준)
    @Query(value = "SELECT p.* FROM products p " +
            "JOIN reviews r ON p.id = r.product_id " +
            "WHERE p.status = 'ACTIVE' " +
            "GROUP BY p.id " +
            "ORDER BY AVG(r.rating) DESC, COUNT(r.id) DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Product> findTop5PopularProducts();

    // 카테고리별 상품 수 카운트
    @Query("SELECT c.id, COUNT(p) " +
            "FROM Product p JOIN p.categories c " +
            "WHERE p.status = 'ACTIVE' " +
            "GROUP BY c.id")
    List<Object[]> countProductsByCategories();
}