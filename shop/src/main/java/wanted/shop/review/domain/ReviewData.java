package wanted.shop.review.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReviewData {
    private int rating;
    private String title;
    private String content;
}
