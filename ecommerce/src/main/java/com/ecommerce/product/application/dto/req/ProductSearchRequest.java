package com.ecommerce.product.application.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearchRequest {
    /**
     * 페이지 번호 (1부터 시작)
     */
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    @Builder.Default
    private Integer page = 1;

    /**
     * 페이지당 항목 수
     */
    @Min(value = 1, message = "페이지당 항목 수는 1 이상이어야 합니다.")
    @Builder.Default
    private Integer perPage = 10;

    /**
     * 정렬 기준 (필드명:정렬방향)
     * 예: created_at:desc, price:asc
     */
    @Pattern(regexp = "^[a-zA-Z_]+(:(asc|desc))?$", message = "정렬 형식이 올바르지 않습니다.")
    @Builder.Default
    private String sort = "created_at:desc";

    /**
     * 상품 상태 필터
     */
    private String status;

    /**
     * 최소 가격 필터
     */
    @Min(value = 0, message = "최소 가격은 0 이상이어야 합니다.")
    private Integer minPrice;

    /**
     * 최대 가격 필터
     */
    @Min(value = 0, message = "최대 가격은 0 이상이어야 합니다.")
    private Integer maxPrice;

    /**
     * 카테고리 ID 목록 필터
     */
    private List<Integer> category;

    /**
     * 판매자 ID 필터
     */
    private Integer seller;

    /**
     * 브랜드 ID 필터
     */
    private Integer brand;

    /**
     * 재고 여부 필터
     */
    private Boolean inStock;

    /**
     * 검색어
     */
    private String search;
    
}
