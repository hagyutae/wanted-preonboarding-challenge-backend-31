package com.dino.cqrs_challenge.presentation.model.rq;

import com.dino.cqrs_challenge.domain.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(description = "상품 검색 조건")
public class ProductSearchCondition {
    @Schema(description = "페이지 번호 (1부터 시작)", example = "1")
    private Integer page = 1;

    @Schema(description = "페이지당 아이템 수", example = "10")
    private Integer perPage = 10;

    @Schema(description = "정렬 기준. 형식: {필드}:{asc|desc}. 여러 개인 경우 콤마로 구분", example = "created_at:desc")
    private String sort = "created_at:desc";

    @Schema(description = "상품 상태 필터 (ACTIVE, OUT_OF_STOCK, DELETED)", example = "ACTIVE")
    private ProductStatus status;

    @Schema(description = "최소 가격 필터", example = "10000")
    private Integer minPrice;

    @Schema(description = "최대 가격 필터", example = "100000")
    private Integer maxPrice;

    @Schema(description = "카테고리 ID 목록 (콤마로 구분)", example = "5,6,7")
    private List<Long> category = new ArrayList<>();

    @Schema(description = "판매자 ID 필터", example = "1")
    private Long seller;

    @Schema(description = "브랜드 ID 필터", example = "2")
    private Long brand;

    @Schema(description = "재고 유무 필터", example = "true")
    private Boolean inStock;

    @Schema(description = "검색어 (상품명, 설명 등)", example = "소파")
    private String search;
}
