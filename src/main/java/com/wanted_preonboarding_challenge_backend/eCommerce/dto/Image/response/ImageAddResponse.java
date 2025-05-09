package com.wanted_preonboarding_challenge_backend.eCommerce.dto.Image.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageAddResponse {
    private String id;
    private String url;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;
    private Long optionId;
}
