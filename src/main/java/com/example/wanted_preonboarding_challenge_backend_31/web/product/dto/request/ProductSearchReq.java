package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ProductSearchReq(
        @Pattern(regexp = "^([a-zA-Z0-9_]+:(asc|desc))(,([a-zA-Z0-9_]+:(asc|desc)))*$")
        String sort,
        ProductStatus status,
        @Min(0)
        Integer minPrice,
        @Min(0)
        Integer maxPrice,
        @Size(min = 1)
        List<Long> category,
        Long seller,
        Long brand,
        Boolean inStock,
        String search,
        List<Long> productIds
) {
}
