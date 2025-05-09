package wanted.shop.review.domain.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ReviewSortField {

    CREATED_AT("timestamps.createdAt"),
    UPDATED_AT("timestamps.updatedAt"),
    DELETED_AT("timestamps.deletedAt"),
    RATING("reviewData.rating"),
    TITLE("reviewData.title"),
    CONTENT("reviewData.content"),
    HELPFUL_VOTES("helpfulVotes"),
    VERIFIED_PURCHASE("verifiedPurchase"),

    PRODUCT_ID("productId.id"),
    USER_ID("user.id"),
    REVIEW_ID("reviewId.id");

    private final String path;

    public static ReviewSortField from(String value) {
        return Arrays.stream(ReviewSortField.values())
                .filter(field -> field.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid sort field: " + value));
    }
}
