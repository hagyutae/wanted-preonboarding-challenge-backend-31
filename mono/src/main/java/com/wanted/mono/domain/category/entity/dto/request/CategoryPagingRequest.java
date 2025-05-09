package com.wanted.mono.domain.category.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPagingRequest {
    private int page = 1;
    private int perPage = 10;
    private String sort = "created_at:desc";
    private boolean includeSubcategories = true;
}
