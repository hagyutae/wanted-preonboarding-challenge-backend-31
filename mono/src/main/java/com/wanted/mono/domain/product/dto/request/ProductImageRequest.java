package com.wanted.mono.domain.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductImageRequest {
    private String url;

    @JsonProperty("alt_text")
    private String altText;

    @JsonProperty("is_primary")
    private Boolean isPrimary;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("option_id")
    private Long optionId; // nullable
}
