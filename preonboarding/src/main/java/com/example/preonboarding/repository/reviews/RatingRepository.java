package com.example.preonboarding.repository.reviews;

import com.example.preonboarding.dto.RatingDTO;

public interface RatingRepository {
    RatingDTO getRatingSummary(Long productId);
}
