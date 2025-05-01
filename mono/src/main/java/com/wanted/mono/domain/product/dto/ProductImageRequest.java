package com.wanted.mono.domain.product.dto;

import lombok.Data;

@Data
public class ProductImageRequest {
    private String url;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;
    private Long optionId; // nullable
}
