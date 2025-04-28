package com.mkhwang.wantedcqrs.product.domain.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

  private Long id;
  private ReviewUserDto user;
  private int rating;
  private String title;
  private String content;
  private boolean verifiedPurchase;
  private int helpfulVotes;
}
