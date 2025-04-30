package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record ProductReviewSearchReq(
        @Pattern(regexp = "^([a-zA-Z0-9_]+:(asc|desc))(,([a-zA-Z0-9_]+:(asc|desc)))*$")
        String sort,
        @Min(1) @Max(5)
        Integer rating
) {
}
