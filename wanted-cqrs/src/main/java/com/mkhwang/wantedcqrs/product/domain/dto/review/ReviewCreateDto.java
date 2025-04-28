package com.mkhwang.wantedcqrs.product.domain.dto.review;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mkhwang.wantedcqrs.product.domain.Product;
import com.mkhwang.wantedcqrs.user.domain.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({"user", "userId", "productId", "product"})
public class ReviewCreateDto {
  @NotEmpty
  private String title;
  @NotEmpty
  private String content;
  @Min(1)
  @Max(5)
  private int rating;

  private Long userId;

  private User user;

  private Long productId;

  private Product product;

}
