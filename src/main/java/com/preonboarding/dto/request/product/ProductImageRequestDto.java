package com.preonboarding.dto.request.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageRequestDto {
    private String url;
    private String altText;
    private boolean isPrimary;
    private int displayOrder;
    private Long optionId;
}
