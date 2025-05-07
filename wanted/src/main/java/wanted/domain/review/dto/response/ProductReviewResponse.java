package wanted.domain.review.dto.response;

import wanted.domain.product.dto.response.Pagination;

import java.util.List;

public record ProductReviewResponse(
        List<ReviewResponse> items,
        SummaryResponse summary,
        Pagination pagination
) {
}
