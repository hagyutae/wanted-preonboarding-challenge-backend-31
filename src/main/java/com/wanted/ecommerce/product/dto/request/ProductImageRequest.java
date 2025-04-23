package com.wanted.ecommerce.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageRequest {
    @NotBlank
    private String url;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;
    private Long optionId;
}
