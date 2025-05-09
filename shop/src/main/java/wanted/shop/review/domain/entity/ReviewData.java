package wanted.shop.review.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class ReviewData {
    private Integer rating;
    private String title;
    private String content;
}
