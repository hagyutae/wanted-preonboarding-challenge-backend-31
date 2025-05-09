package com.wanted_preonboarding_challenge_backend.eCommerce.dto.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ImageDetailResponse {
    private Long id;
    private String url;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;
    private Long optionId;  // null 가능성을 고려
}