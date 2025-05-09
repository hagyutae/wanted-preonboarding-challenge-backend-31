package wanted.shop.review.dto;

import lombok.Setter;
import wanted.shop.review.domain.entity.ReviewData;

@Setter
public class ReviewCreateRequest {
    private Integer rating;
    private String title;
    private String content;

    public ReviewData toReviewData() {
        return ReviewData.builder()
                .rating(rating)
                .title(title)
                .content(content)
                .build();
    }
}
