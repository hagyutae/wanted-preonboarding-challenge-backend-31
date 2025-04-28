package com.mkhwang.wantedcqrs.product.domain.dto.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCreateResponseDto {
  private Long id;
  private String name;
  private String slug;
  private Instant createdAt;
  private Instant updatedAt;
}
