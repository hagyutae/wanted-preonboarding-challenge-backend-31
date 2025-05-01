package com.dino.cqrs_challenge.domain.model.dto;

import com.dino.cqrs_challenge.domain.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Schema(description = "상품 DTO")
public class FindProductSummaryDTO {

    @Schema(description = "상품 식별번호")
    private Long id;

    @Schema(description = "상품명")
    private String name;

    @Schema(description = "슬러그")
    private String slug;

    @Schema(description = "짧은 설명")
    private String shortDescription;

    @Schema(description = "기본 가격")
    private Integer basePrice;

    @Schema(description = "할인 가격")
    private Integer salePrice;

    @Schema(description = "통화")
    private String currency;

    @Schema(description = "대표 이미지")
    private ProductImageSummaryDTO primaryImage;

    @Schema(description = "상품 브랜드")
    private BrandSummaryDTO brand;

    @Schema(description = "판매자 요약 정보")
    private SellerSummaryDTO seller;

    @Schema(description = "평점")
    private BigDecimal rating;

    @Schema(description = "리뷰 수")
    private Integer reviewCount;

    @Schema(description = "재고 여부")
    private Boolean inStock;

    @Schema(description = "상품 상태")
    private ProductStatus status;

    @Schema(description = "생성 시각")
    private LocalDateTime createdAt;

}
