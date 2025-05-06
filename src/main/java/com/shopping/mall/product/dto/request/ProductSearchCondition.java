package com.shopping.mall.product.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductSearchCondition {

    private Integer page = 1;
    private Integer perPage = 10;
    private String sort = "createdAt:desc";

    private String status;
    private Integer minPrice;
    private Integer maxPrice;
    private List<Long> category;
    private Long sellerId;
    private Long brandId;
    private Boolean inStock;
    private String search;

    // 태그 필터 추가
    private List<Long> tagIds;
}
