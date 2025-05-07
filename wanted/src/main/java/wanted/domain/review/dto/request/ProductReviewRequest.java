package wanted.domain.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductReviewRequest(
        @NotNull
        @Min(0) @Max(5)
        int rating,

        @NotNull
        String title,

        @NotNull
        String content
) {
}
