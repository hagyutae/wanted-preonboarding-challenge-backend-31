package com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.Image.ImageDetailResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.RatingDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.tag.TagDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.brand.BrandDetailDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.category.response.ProductCategoryResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.detail.DetailDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.optionGroup.ProductOptionGroupResponse;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.price.PriceDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.seller.SellerDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse {
    private Long id;
    private String name;
    private String slug;
    @JsonProperty("short_description")
    private String shortDescription;
    @JsonProperty("full_description")
    private String fullDescription;
    private SellerDetailDto seller;
    private BrandDetailDto brand;
    private String status;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    private DetailDto detail;
    private PriceDto price;
    private List<ProductCategoryResponse> categories;
    private List<ProductOptionGroupResponse> optionGroups;
    private List<ImageDetailResponse> images;
    private List<TagDto> tags;
    private RatingDto rating;
}
