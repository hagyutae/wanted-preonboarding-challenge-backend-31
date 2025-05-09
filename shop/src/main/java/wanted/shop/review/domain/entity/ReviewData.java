package wanted.shop.review.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewData {
    private Integer rating;
    private String title;
    private String content;

    @Override
    public String toString() {
        return "ReviewData{" +
                "rating=" + rating +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
