package com.example.preonboarding.reviews.repository;

import com.example.preonboarding.reviews.dto.RatingDTO;

public interface RatingRepository {
    RatingDTO getRatingSummary(Long productId);
}
