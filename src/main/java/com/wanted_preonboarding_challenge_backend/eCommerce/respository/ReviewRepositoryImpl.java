package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Product;
import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    @Override
    public Page<Review> findByProductAndRating(Product product, Pageable pageable, Integer rating) {
        return null;
    }
}
