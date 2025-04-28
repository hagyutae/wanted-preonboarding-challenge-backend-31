package com.ecommerce.review.infrastructure;

import com.ecommerce.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    Page<Review> findByProductId(Long productId, Pageable pageable);
    
    List<Review> findByUserId(Long userId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId")
    Double getAverageRatingByProductId(@Param("productId") Long productId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId")
    Long getReviewCountByProductId(@Param("productId") Long productId);
    
    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.rating = :rating")
    List<Review> findByProductIdAndRating(@Param("productId") Long productId, @Param("rating") Integer rating);
    
    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.verifiedPurchase = true")
    List<Review> findVerifiedPurchaseReviewsByProductId(@Param("productId") Long productId);
    
    @Query("SELECT r FROM Review r WHERE r.createdAt >= :fromDate")
    List<Review> findReviewsCreatedAfter(@Param("fromDate") LocalDateTime fromDate);
    
    @Query(value = "SELECT r.* FROM reviews r " +
                  "WHERE r.product_id = :productId " +
                  "ORDER BY r.helpful_votes DESC, r.created_at DESC " +
                  "LIMIT :limit", 
           nativeQuery = true)
    List<Review> findMostHelpfulReviews(@Param("productId") Long productId, @Param("limit") int limit);
    
    @Query(value = "SELECT p.id, COUNT(r.id) as review_count, AVG(r.rating) as avg_rating " +
                  "FROM products p " +
                  "JOIN reviews r ON p.id = r.product_id " +
                  "GROUP BY p.id " +
                  "HAVING AVG(r.rating) >= :minRating " +
                  "ORDER BY review_count DESC, avg_rating DESC " +
                  "LIMIT :limit", 
           nativeQuery = true)
    List<Object[]> findTopRatedProducts(@Param("minRating") Double minRating, @Param("limit") int limit);
}
