package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductImageCreateReq(
        @NotBlank @Size(max = 255)
        String url,
        @Size(max = 255)
        String altText,
        boolean isPrimary,
        int displayOrder,
        Long optionId
) {
}
