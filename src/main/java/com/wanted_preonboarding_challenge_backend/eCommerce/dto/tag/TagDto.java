package com.wanted_preonboarding_challenge_backend.eCommerce.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TagDto {
    private Long id;
    private String name;
    private String slug;
}