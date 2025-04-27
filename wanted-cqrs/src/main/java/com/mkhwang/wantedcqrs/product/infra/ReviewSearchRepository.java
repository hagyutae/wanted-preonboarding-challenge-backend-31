package com.mkhwang.wantedcqrs.product.infra;

import com.mkhwang.wantedcqrs.product.domain.dto.ProductRatingDto;

public interface ReviewSearchRepository {
  ProductRatingDto findAverageRatingByProductId(Long productId);
}
