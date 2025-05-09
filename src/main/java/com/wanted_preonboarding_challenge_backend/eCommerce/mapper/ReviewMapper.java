package com.wanted_preonboarding_challenge_backend.eCommerce.mapper;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Review;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.ReviewItemDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.request.ReviewCreateRequest;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.response.ReviewCreateResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.review.response.ReviewUpdateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewMapper {
    @Mapping(target = "product", ignore = true)
    Review toEntity(ReviewCreateRequest dto);

    ReviewCreateResponse toReviewCreateResponse(Review review);
    ReviewUpdateResponse toReviewUpdateResponse(Review review);

    List<ReviewItemDto> toReviewItemDtoList(List<Review> reviews);
}
