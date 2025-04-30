package com.mkhwang.wantedcqrs.config.advice.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ApiPageData<T> {
  private List<T> items;
  private ApiPageInfo pageInfo;

  private ApiPageData() {
  }

  public ApiPageData(Page<T> pageData) {
    this.items = pageData.getContent();
    this.pageInfo = ApiPageInfo.of(
            pageData.getTotalElements(),
            pageData.getTotalPages(),
            pageData.getPageable().isPaged() ? pageData.getPageable().getPageNumber() + 1 : 1,
            pageData.getSize()
    );
  }
}
