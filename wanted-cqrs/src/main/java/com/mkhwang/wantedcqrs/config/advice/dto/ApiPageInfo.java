package com.mkhwang.wantedcqrs.config.advice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
