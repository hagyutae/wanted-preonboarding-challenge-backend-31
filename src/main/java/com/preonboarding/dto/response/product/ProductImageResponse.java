package com.preonboarding.dto.response.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductImageResponse {
    private Long id;
    private String url;
    private String altText;
    private boolean isPrimary;
    private int displayOrder;
    private Long optionId;
}
