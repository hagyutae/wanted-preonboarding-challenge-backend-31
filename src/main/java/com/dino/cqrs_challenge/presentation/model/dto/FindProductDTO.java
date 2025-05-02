package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.Product;
import com.dino.cqrs_challenge.domain.entity.ProductDetail;
import com.dino.cqrs_challenge.domain.entity.ProductPrice;
import com.dino.cqrs_challenge.domain.entity.Review;
import com.dino.cqrs_challenge.domain.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "상품 상세 조회 DTO")
public class FindProductDTO {

    @Schema(description = "상품 식별번호")
    private Long id;

    @Schema(description = "상품명")
    private String name;

    @Schema(description = "슬러그 (SEO friendly ID)")
    private String slug;

    @Schema(description = "짧은 설명")
    private String shortDescription;

    @Schema(description = "전체 설명")
    private String fullDescription;

    @Schema(description = "판매자 정보")
    private FindSellerDTO seller;

    @Schema(description = "브랜드 정보")
    private FindBrandDTO brand;

    @Schema(description = "상품 상태")
    private ProductStatus status;

    @Schema(description = "생성 시각")
    private LocalDateTime createdAt;

    @Schema(description = "수정 시각")
    private LocalDateTime updatedAt;

    @Schema(description = "상품 상세 정보")
    private FindProductDetailDTO detail;

    @Schema(description = "상품 가격")
    private FindProductPriceDTO price;

    @Schema(description = "카테고리 목록")
    private List<FindProductCategoryDTO> categories = new ArrayList<>();

    @Schema(description = "옵션 그룹 목록")
    private List<FindProductOptionGroupDTO> optionGroups = new ArrayList<>();

    @Schema(description = "상품 이미지 목록")
    private List<FindProductImageDTO> images = new ArrayList<>();

    @Schema(description = "상품 태그 목록")
    private List<FindProductTagDTO> tags = new ArrayList<>();

    @Schema(description = "상품 평점 정보")
    private ProductRatingDTO rating;

    @Schema(description = "연관 상품 정보")
    private List<RelatedProductDto> relatedProducts = new ArrayList<>();

    public static FindProductDTO of(Product product, ProductDetail productDetail, ProductPrice productPrice) {
        FindProductDTO findProductDTO = new FindProductDTO();
        findProductDTO.id = product.getId();
        findProductDTO.name = product.getName();
        findProductDTO.slug = product.getSlug();
        findProductDTO.shortDescription = product.getShortDescription();
        findProductDTO.fullDescription = product.getFullDescription();
        findProductDTO.seller = FindSellerDTO.from(product.getSeller());
        findProductDTO.brand = FindBrandDTO.from(product.getBrand());
        findProductDTO.status = product.getStatus();
        findProductDTO.createdAt = product.getCreatedAt();
        findProductDTO.updatedAt = product.getUpdatedAt();
        findProductDTO.detail = FindProductDetailDTO.from(productDetail);
        findProductDTO.price = FindProductPriceDTO.from(productPrice);
        findProductDTO.categories = product.getProductCategories().stream()
                .map(FindProductCategoryDTO::from)
                .toList();
        findProductDTO.optionGroups = product.getProductOptionGroups().stream()
                .map(FindProductOptionGroupDTO::from)
                .toList();

        findProductDTO.images = product.getProductImages().stream()
                .map(FindProductImageDTO::from)
                .toList();
        findProductDTO.tags = product.getProductTags().stream()
                .map(FindProductTagDTO::from)
                .toList();
        List<Review> reviews = product.getReviews();
        findProductDTO.rating = ProductRatingDTO.from(reviews);
        return findProductDTO;
    }
}

