package com.example.demo.review.dto;

import com.example.demo.common.response.PageResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public record ReviewSummaryResponse(
        List<ReviewSummary> items,
        ReviewSummaryDistribution summary,
        PageResponse.PageNation pagination
) {
    public static ReviewSummaryResponse of(Page<ReviewSummary> reviewSummaryPage, ReviewSummaryDistribution summary) {
        return new ReviewSummaryResponse(
                reviewSummaryPage.getContent(),
                summary,
                new PageResponse.PageNation(
                        reviewSummaryPage.getTotalElements(),
                        (long) reviewSummaryPage.getTotalPages(),
                        (long) reviewSummaryPage.getNumber() + 1,
                        (long) reviewSummaryPage.getSize()
                )
        );
    }
}
