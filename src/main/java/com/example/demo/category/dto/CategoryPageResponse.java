package com.example.demo.category.dto;

import com.example.demo.common.response.PageResponse;
import com.example.demo.product.dto.ProductSummary;
import org.springframework.data.domain.Page;

import java.util.List;

public record CategoryPageResponse(
        List<ProductSummary> items,
        CategorySummary category,
        PageResponse.PageNation pagination
) {
    public static CategoryPageResponse of(Page<ProductSummary> productSummaryPage, CategorySummary categorySummary) {
        return new CategoryPageResponse(
                productSummaryPage.getContent(),
                categorySummary,
                new PageResponse.PageNation(
                        productSummaryPage.getTotalElements(),
                        (long) productSummaryPage.getTotalPages(),
                        (long) productSummaryPage.getNumber() + 1,
                        (long) productSummaryPage.getSize()
                )
        );
    }
}
