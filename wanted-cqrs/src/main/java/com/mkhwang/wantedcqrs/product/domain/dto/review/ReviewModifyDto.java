package com.mkhwang.wantedcqrs.product.domain.dto.review;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({"userId", "id"})
public class ReviewModifyDto {
  @NotEmpty
  private String title;
  @NotEmpty
  private String content;
  @Min(1)
  @Max(5)
  private int rating;

  private Long userId;

  private Long id;
}
