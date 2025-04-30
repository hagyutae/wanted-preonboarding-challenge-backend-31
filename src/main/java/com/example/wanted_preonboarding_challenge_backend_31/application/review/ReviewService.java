package com.example.wanted_preonboarding_challenge_backend_31.application.review;

import com.example.wanted_preonboarding_challenge_backend_31.web.review.dto.request.ReviewUpdateReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.review.dto.response.ReviewUpdateRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    public ReviewUpdateRes update(Long reviewId, ReviewUpdateReq reviewUpdateReq) {
        return null;
    }
}
