package com.mkhwang.wantedcqrs.config.advice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiPageInfo {
  @JsonProperty("total_items")
  private long totalItems;
  @JsonProperty("total_pages")
  private long totalPages;
  @JsonProperty("current_page")
  private long currentPage;
  @JsonProperty("per_page")
  private long perPAge;

  public static ApiPageInfo of(long totalItems, long totalPages, long currentPage, long perPage) {
    return new ApiPageInfo(totalItems, totalPages, currentPage, perPage);
  }

  public static ApiPageInfo empty() {
    return new ApiPageInfo(0, 0, 1, 10);
  }

  public static ApiPageInfo empty(Pageable pageable) {
    return new ApiPageInfo(0, 0, 1, pageable.getPageSize());
  }
}
