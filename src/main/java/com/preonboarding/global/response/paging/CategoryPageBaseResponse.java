package com.preonboarding.global.response.paging;

import com.preonboarding.dto.response.category.CategoryResponseWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryPageBaseResponse<T> {
    private boolean success;
    private CategoryResponseWrapper data;
    private List<T> items;
    private PaginationDto pagination;
    private String message;
}
