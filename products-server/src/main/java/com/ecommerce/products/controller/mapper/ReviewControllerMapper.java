package com.ecommerce.products.controller.mapper;

import com.ecommerce.products.application.dto.ReviewDto;
import com.ecommerce.products.controller.dto.ReviewCreateRequest;
import com.ecommerce.products.controller.dto.ReviewUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class ReviewControllerMapper {

    public ReviewDto.CreateRequest toReviewDtoCreateRequest(ReviewCreateRequest request) {
        return ReviewDto.CreateRequest.builder()
                .rating(request.rating())
                .title(request.title())
                .content(request.content())
                .build();
    }

    public ReviewDto.UpdateRequest toReviewDtoUpdateRequest(ReviewUpdateRequest request) {
        return ReviewDto.UpdateRequest.builder()
                .rating(request.rating())
                .content(request.content())
                .build();
    }

}