package com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductCreateRequest {

    private String name;

    private String slug;

    private String shortDescription;

    private String fullDescription;

    private Long sellerId;

    private Long brandId;

    private String status;

    private ProductDetailCreateRequest detail;

    private ProductPriceCreateRequest price;

    private List<ProductCategoryCreateRequest> categories;

    private List<ProductOptionGroupCreateRequest> optionGroups;

    private List<ProductImageCreateRequest> images;

    private List<Long> tags;
}
