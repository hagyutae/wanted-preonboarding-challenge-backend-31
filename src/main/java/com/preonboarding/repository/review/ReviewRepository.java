package com.preonboarding.repository.review;

import com.preonboarding.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("select r from Review r where r.product.id = :id and (:rating IS NULL OR r.rating >= :rating)")
    Page<Review> findReviewsWithPaging(@Param("id") Long id,Pageable pageable, @Param("rating") Integer rating);
}
