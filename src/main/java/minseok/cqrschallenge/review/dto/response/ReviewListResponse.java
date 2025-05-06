package minseok.cqrschallenge.review.dto.response;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import minseok.cqrschallenge.common.dto.PaginationResponse;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewListResponse {

    private List<ReviewResponse> items;

    private ReviewSummary summary;

    private PaginationResponse.Pagination pagination;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewSummary {

        private Double averageRating;

        private Integer totalCount;

        private Map<Integer, Integer> distribution;
    }
}