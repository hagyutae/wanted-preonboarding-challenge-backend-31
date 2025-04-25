package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductStatus;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductCategoryDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductImageDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductOptionGroupDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductPriceDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProductCreateReq(
        @NotBlank
        String name,
        @NotBlank
        String slug,
        String shortDescription,
        String fullDescription,
        Long sellerId,
        Long brandId,
        @NotNull
        ProductStatus status,
        @Valid @NotNull
        ProductDetailDto detail,
        @Valid @NotNull
        ProductPriceDto price,
        @NotNull
        List<ProductCategoryDto> categories,
        @Valid @NotNull
        List<ProductOptionGroupDto> optionGroups,
        @Valid @NotNull
        List<ProductImageDto> images,
        @NotNull
        List<Long> tags
) {
}
