package com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductUpdateResponse {
    private Long id;
    private String name;
    private String slug;
    private LocalDateTime updatedAt;
}
