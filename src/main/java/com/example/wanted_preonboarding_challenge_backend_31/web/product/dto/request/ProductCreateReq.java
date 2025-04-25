package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductStatus;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductCategoryDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductImageDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductOptionGroupDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductPriceDto;
import java.util.List;

public record ProductCreateReq(
        String name,
        String slug,
        String shortDescription,
        String fullDescription,
        Long sellerId,
        Long brandId,
        ProductStatus status,
        ProductDetailDto detail,
        ProductPriceDto price,
        List<ProductCategoryDto> categories,
        List<ProductOptionGroupDto> optionGroups,
        List<ProductImageDto> images,
        List<Long> tags
) {
}
