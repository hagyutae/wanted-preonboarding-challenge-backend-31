package com.wanted.ecommerce.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {

    private int totalItems;
    private int totalPages;
    private int currentPage;
    private int perPage;
}
