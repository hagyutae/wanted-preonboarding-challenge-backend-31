package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.Product;
import com.dino.cqrs_challenge.domain.entity.ProductImage;
import com.dino.cqrs_challenge.domain.entity.ProductPrice;
import com.dino.cqrs_challenge.domain.entity.Review;
import com.dino.cqrs_challenge.domain.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Schema(description = "상품 DTO")
public class FindProductSearchDTO {

    @Schema(description = "상품 식별번호")
    private Long id;

    @Schema(description = "상품명")
    private String name;

    @Schema(description = "슬러그")
    private String slug;

    @Schema(description = "짧은 설명")
    private String shortDescription;

    @Schema(description = "기본 가격")
    private BigDecimal basePrice;

    @Schema(description = "할인 가격")
    private BigDecimal salePrice;

    @Schema(description = "통화")
    private String currency;

    @Schema(description = "대표 이미지")
    private ProductImageSummaryDTO primaryImage;

    @Schema(description = "상품 브랜드")
    private BrandSummaryDTO brand;

    @Schema(description = "판매자 요약 정보")
    private SellerSummaryDTO seller;

    @Schema(description = "평점")
    private Double rating;

    @Schema(description = "리뷰 수")
    private Integer reviewCount = 0;

    @Schema(description = "재고 여부")
    private Boolean inStock;

    @Schema(description = "상품 상태")
    private ProductStatus status;

    @Schema(description = "생성 시각")
    private LocalDateTime createdAt;

    public static FindProductSearchDTO of(Product product, ProductPrice productPrice, List<Review> reviewList, ProductImage primaryImage) {
        FindProductSearchDTO findProductSearchDTO = new FindProductSearchDTO();
        findProductSearchDTO.id = product.getId();
        findProductSearchDTO.name = product.getName();
        findProductSearchDTO.slug = product.getSlug();
        findProductSearchDTO.shortDescription = product.getShortDescription();
        if (productPrice != null) {
            findProductSearchDTO.basePrice = productPrice.getBasePrice();
            findProductSearchDTO.salePrice = productPrice.getSalePrice();
            findProductSearchDTO.currency = productPrice.getCurrency();
        }
        findProductSearchDTO.primaryImage = ProductImageSummaryDTO.from(primaryImage);

        findProductSearchDTO.brand = BrandSummaryDTO.from(product.getBrand());
        findProductSearchDTO.seller = SellerSummaryDTO.from(product.getSeller());
        findProductSearchDTO.rating = calculateAverageRating(reviewList);
        findProductSearchDTO.reviewCount = reviewList.size();
        findProductSearchDTO.status = product.getStatus();
        findProductSearchDTO.createdAt = product.getCreatedAt();
        findProductSearchDTO.inStock = product.getProductOptionGroups().stream()
                .anyMatch(productOptionGroup -> productOptionGroup.getOptions().stream()
                        .anyMatch(option -> option.getStock() > 0));

        return findProductSearchDTO;
    }

    public static double calculateAverageRating(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0; // 리뷰가 없으면 0점 처리
        }

        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0); // 평균값이 없으면 0.0
    }
}
