package com.wanted.mono.domain.product.dto.request;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearchRequest {
    // 페이지 번호
    @Builder.Default
    private Integer page = 1;

    // 페이지 당 아이템 수
    @Builder.Default
    private Integer perPage = 10;

    // 정렬 기준. 형식: {필드}:{asc|desc}. 여러 개인 경우 콤마로 구분
    @Builder.Default
    private String sort = "createdAt:desc";

    // 상품 상태 필터 (ACTIVE, OUT_OF_STOCK, DELETED)
    private String status;

    // 최소 가격 필터
    private Integer minPrice;

    // 최대 가격 필터
    private Integer maxPrice;

    // 카테고리 ID 필터 (여러 개인 경우 콤마로 구분)
    private List<Long> category;

    // 판매자 ID 필터
    private Long seller;

    // 브랜드 ID 필터
    private Long brand;

    // 재고 유무 필터
    private Boolean inStock;

    // 검색어
    private String search;
}

