package com.mkhwang.wantedcqrs.product.domain.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageCreateDto {
  @NotEmpty
  private String url;
  @JsonProperty("alt_text")
  private String altText;
  @JsonProperty("is_primary")
  private boolean primary;
  @Min(1)
  @JsonProperty("display_order")
  private int displayOrder;
  @JsonProperty("option_id")
  private Long optionId;
}
