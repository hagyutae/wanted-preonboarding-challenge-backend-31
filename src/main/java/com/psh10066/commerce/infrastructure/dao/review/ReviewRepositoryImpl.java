package com.psh10066.commerce.infrastructure.dao.review;

import com.psh10066.commerce.domain.model.review.Review;
import com.psh10066.commerce.domain.model.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public List<Review> findAllByProductId(Long productId) {
        return reviewJpaRepository.findAllByProductId(productId);
    }
}
