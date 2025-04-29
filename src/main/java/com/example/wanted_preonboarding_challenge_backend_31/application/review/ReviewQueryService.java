package com.example.wanted_preonboarding_challenge_backend_31.application.review;

import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;

    public List<Long> getPopularProductIds(int limit) {
        return reviewRepository.findPopularProductIds(limit);
    }
}
