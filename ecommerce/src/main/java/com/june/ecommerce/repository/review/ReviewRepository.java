package com.june.ecommerce.repository.review;

import com.june.ecommerce.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
