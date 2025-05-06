package com.shopping.mall.review.dto.request;

import lombok.Getter;

@Getter
public class ReviewCreateRequest {

    private Long userId;

    private Integer rating;

    private String title;

    private String content;

    private Boolean verifiedPurchase;
}
