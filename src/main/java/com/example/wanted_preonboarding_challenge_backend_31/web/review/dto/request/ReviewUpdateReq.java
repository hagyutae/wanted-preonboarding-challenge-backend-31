package com.example.wanted_preonboarding_challenge_backend_31.web.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReviewUpdateReq(
        int rating,
        @NotBlank @Size(max = 255)
        String title,
        @NotBlank
        String content
) {
}
