package com.mkhwang.wantedcqrs.product.domain.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageCreateResponseDto {
  private Long id;
  @NotEmpty
  private String url;
  @JsonProperty("alt_text")
  private String altText;
  @JsonProperty("is_primary")
  private boolean primary;
  @JsonProperty("display_order")
  private int displayOrder;
  @JsonProperty("option_id")
  private Integer optionId;
}
