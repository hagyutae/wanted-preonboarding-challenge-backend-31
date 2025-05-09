package wanted.shop.product.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import wanted.shop.review.domain.entity.ReviewTimestamps;

import java.time.LocalDateTime;

@Getter
@Embeddable
public class ProductTimestamps {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductTimestamps createNow() {
        ProductTimestamps productTimestamps = new ProductTimestamps();
        productTimestamps.createdAt = LocalDateTime.now();
        productTimestamps.updatedAt = LocalDateTime.now();
        return productTimestamps;
    }

    public void markUpdated() {
        this.updatedAt = LocalDateTime.now();
    }

}
