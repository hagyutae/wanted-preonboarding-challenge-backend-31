package com.shopping.mall.review.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewSearchCondition {

    private Integer page = 1;

    private Integer perPage = 10;

    private String sort = "createdAt:desc";

    private Integer rating; // 평점 필터
}