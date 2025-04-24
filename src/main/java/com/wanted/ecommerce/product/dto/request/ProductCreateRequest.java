package com.wanted.ecommerce.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    @NotBlank
    private String status;
    private ProductDetailRequest detail;
    private ProductPriceRequest price;
    private List<ProductCategoryRequest> categories;
    private List<ProductOptionGroupRequest> optionGroups;
    private List<ProductImageRequest> images;
    private List<Integer> tags;
}
