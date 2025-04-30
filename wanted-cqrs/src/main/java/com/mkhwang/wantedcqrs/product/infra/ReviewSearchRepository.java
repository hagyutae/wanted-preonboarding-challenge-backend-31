package com.mkhwang.wantedcqrs.product.infra;

import com.mkhwang.wantedcqrs.product.domain.dto.ProductRatingDto;
import com.mkhwang.wantedcqrs.product.domain.dto.review.ReviewSearchDto;
import com.mkhwang.wantedcqrs.product.domain.dto.review.ReviewSearchResultDto;

public interface ReviewSearchRepository {
  ProductRatingDto findAverageRatingByProductId(Long productId);

  ReviewSearchResultDto findRatingByProductId(Long productId, ReviewSearchDto searchDto);
}
