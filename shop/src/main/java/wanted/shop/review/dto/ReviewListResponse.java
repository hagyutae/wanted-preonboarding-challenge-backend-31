package wanted.shop.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import wanted.shop.common.api.Pagination;

import java.util.List;

@Getter
@JsonPropertyOrder({ "items", "summary", "pagination" })
public class ReviewListResponse {

    @JsonProperty("items")
    private final List<ReviewDto> items;

    @JsonProperty("summary")
    private final ReviewSummaryDto summary;

    @JsonProperty("pagination")
    private final Pagination pagination;

    public ReviewListResponse(List<ReviewDto> items, ReviewSummaryDto summary, Pagination pagination) {
        this.items = items;
        this.summary = summary;
        this.pagination = pagination;
    }
}