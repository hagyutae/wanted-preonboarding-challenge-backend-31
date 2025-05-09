package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Product;
import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReviewRepositoryCustom {
    Page<Review> findByProductAndRating(Product product, Pageable pageable, Integer rating);
}
