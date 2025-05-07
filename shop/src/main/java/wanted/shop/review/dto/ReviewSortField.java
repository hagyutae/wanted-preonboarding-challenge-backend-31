package wanted.shop.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ReviewSortField {

    CREATED_AT("date.createdAt"),
    UPDATED_AT("date.updatedAt"),
    RATING("data.rating"),
    TITLE("data.title"),
    CONTENT("data.content"),
    HELPFUL_VOTES("helpfulVotes"),
    VERIFIED_PURCHASE("verifiedPurchase"),

    PRODUCT_ID("productId.id"),
    USER_ID("userId.id"),
    REVIEW_ID("reviewId.id");

    private final String path;

    public static ReviewSortField from(String value) {
        return Arrays.stream(ReviewSortField.values())
                .filter(field -> field.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid sort field: " + value));
    }
}
