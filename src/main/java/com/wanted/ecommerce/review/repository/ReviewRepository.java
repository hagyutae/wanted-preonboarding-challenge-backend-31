package com.wanted.ecommerce.review.repository;

import com.wanted.ecommerce.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select avg(re.rating) from Review re where re.product.id = :productId")
    Double getAvgRatingByProductId(Long productId);

    @Query("select count(re) from Review re where re.product.id = :productId")
    Long getReviewCountByProductId(Long productId);
}
