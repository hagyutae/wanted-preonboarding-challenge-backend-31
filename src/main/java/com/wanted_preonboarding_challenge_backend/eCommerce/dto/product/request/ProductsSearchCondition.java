package com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductsSearchCondition {

    private Integer page = 1;                        // 기본값 1
    private Integer perPage = 10;                    // 기본값 10
    private String sort = "created_at:desc";         // 정렬 기준 (예: created_at:desc)
    private String status;                           // ACTIVE, OUT_OF_STOCK, DELETED
    private Integer minPrice;
    private Integer maxPrice;
    private List<Integer> category;                  // category=1,2,3 → @RequestParam List<Integer>
    private Integer seller;
    private Integer brand;
    private Boolean inStock;                         // true: 재고 있음
    private String search;                           // 검색어
}
