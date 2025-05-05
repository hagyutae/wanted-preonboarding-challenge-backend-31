package com.example.cqrsapp.product.repository;

import com.example.cqrsapp.product.domain.ProductStatus;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchParm {
    private ProductStatus status; // 상품 상태 (ex: ACTIVE)
    private Long minPrice;        // 최소 가격
    private Long maxPrice;        // 최대 가격
    private List<Long> category;        // 카테고리 ID
    private Long seller;          // 판매자 ID
    private Long brand;           // 브랜드 ID
    private Boolean inStock;      // 재고 있음 여부
    private String search;        // 검색어
}