package com.example.preonboarding.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequest {
    int page = 1; //페이지 번호
    int perPage = 10; // 페이지당 아이템 수
    String  sort = "created_at:desc"; //정렬기준
    String status; // 상품 상태 필터
    Integer minPrice; // 최소 가격 필터
    Integer maxPrice; // 최대 가격 필터
    int[] category; // 카테고리 ID
    Long seller; // 판매자 ID
    Long brand; //브랜드 ID
    Boolean inStock; //재고 유무
    String search; //검색어
}
