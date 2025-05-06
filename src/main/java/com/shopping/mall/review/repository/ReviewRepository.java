package com.shopping.mall.review.repository;

import com.shopping.mall.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
        SELECT r FROM Review r
        WHERE r.product.id = :productId
          AND (:rating IS NULL OR r.rating = :rating)
        ORDER BY r.createdAt DESC
        """)
    List<Review> findByProductId(@Param("productId") Long productId,
                                 @Param("rating") Integer rating,
                                 @Param("offset") int offset,
                                 @Param("limit") int limit);

    @Query("""
        SELECT COUNT(r) FROM Review r
        WHERE r.product.id = :productId
          AND (:rating IS NULL OR r.rating = :rating)
        """)
    Long countByProductId(@Param("productId") Long productId, @Param("rating") Integer rating);

    @Query("""
        SELECT r.rating, COUNT(r)
        FROM Review r
        WHERE r.product.id = :productId
        GROUP BY r.rating
        """)
    List<Object[]> findRatingDistribution(@Param("productId") Long productId);

    @Query("""
        SELECT AVG(r.rating) FROM Review r
        WHERE r.product.id = :productId
        """)
    Double getAverageRating(@Param("productId") Long productId);

    default Map<Integer, Long> getRatingDistribution(Long productId) {
        List<Object[]> result = findRatingDistribution(productId);
        Map<Integer, Long> map = new HashMap<>();
        for (Object[] row : result) {
            map.put((Integer) row[0], (Long) row[1]);
        }
        return map;
    }
}