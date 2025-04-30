package com.mkhwang.wantedcqrs.product.domain.dto.review;

import com.mkhwang.wantedcqrs.config.advice.dto.ApiPageInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewSearchResultDto {
  private List<ReviewSearchItemDto> items;
  private ReviewSearchSummaryDto summary;
  private ApiPageInfo pagination;

  private ReviewSearchResultDto(List<ReviewSearchItemDto> items, ReviewSearchSummaryDto summary, ApiPageInfo pagination) {
    this.items = items;
    this.summary = summary;
    this.pagination = pagination;
  }

  public static ReviewSearchResultDto of(List<ReviewSearchItemDto> items, ReviewSearchSummaryDto summary, ApiPageInfo pagination) {
    return new ReviewSearchResultDto(items, summary, pagination);
  }
}
