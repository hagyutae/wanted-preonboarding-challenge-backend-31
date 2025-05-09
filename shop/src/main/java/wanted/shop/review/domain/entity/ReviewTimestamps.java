package wanted.shop.review.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Embeddable
public class ReviewTimestamps {

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public static ReviewTimestamps createNow() {
        ReviewTimestamps reviewTimestamps = new ReviewTimestamps();
        reviewTimestamps.createdAt = LocalDateTime.now();
        reviewTimestamps.updatedAt = LocalDateTime.now();
        return reviewTimestamps;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
