package wanted.domain.review.dto.response;

import java.math.BigDecimal;
import java.util.Map;


public record SummaryResponse(
    BigDecimal averageRating,
    Integer totalCount,
    Map<String, Integer> distribution
) {

}
