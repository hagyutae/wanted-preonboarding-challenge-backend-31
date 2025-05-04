package com.psh10066.commerce.domain.model.review;

import java.util.List;

public interface ReviewRepository {

    List<Review> findAllByProductId(Long productId);
}
