package com.psh10066.commerce.api.dto.response;

import com.psh10066.commerce.domain.model.review.ReviewFirstCollection;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public record PaginationWithSummaryResponse<T>(
    SummaryDto summary,
    List<T> items,
    Pagination pagination
) {

    public static <T, R> PaginationWithSummaryResponse<R> of(ReviewFirstCollection totalReviews, Page<T> page, Function<T, R> mapper) {
        return new PaginationWithSummaryResponse<>(
            new SummaryDto(
                totalReviews.getAverage(),
                totalReviews.getSize(),
                totalReviews.getDistribution()
            ),
            page.getContent().stream().map(mapper).toList(),
            new Pagination(page.getTotalElements(), page.getTotalPages(), page.getNumber() + 1, page.getSize())
        );
    }

    public record SummaryDto(
        BigDecimal averageRating,
        Integer totalCount,
        ReviewFirstCollection.Distribution distribution
    ) {
    }

    public record Pagination(
        long totalItems,
        int totalPages,
        int currentPage,
        int perPage
    ) {
    }
}