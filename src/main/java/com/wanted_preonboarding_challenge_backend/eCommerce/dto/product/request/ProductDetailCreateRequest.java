package com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailCreateRequest {
    private Double weight;
    private Map<String, Object> dimensions;  // width, height, depth
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private Map<String, Object> additionalInfo; // e.g., assembly_required, assembly_time
}
