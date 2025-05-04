package com.psh10066.commerce.infrastructure.dao.review;

import com.psh10066.commerce.domain.model.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProductId(Long productId);
}
