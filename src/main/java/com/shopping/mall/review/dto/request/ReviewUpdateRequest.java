package com.shopping.mall.review.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequest {

    private Integer rating;

    private String title;

    private String content;
}