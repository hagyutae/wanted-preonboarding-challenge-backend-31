package com.example.wanted_preonboarding_challenge_backend_31.web.category.dto.request;

import jakarta.validation.constraints.Pattern;

public record CategoryProductReq(
        @Pattern(regexp = "^([a-zA-Z0-9_]+:(asc|desc))(,([a-zA-Z0-9_]+:(asc|desc)))*$")
        String sort,
        Boolean includeSubcategories
) {
}
