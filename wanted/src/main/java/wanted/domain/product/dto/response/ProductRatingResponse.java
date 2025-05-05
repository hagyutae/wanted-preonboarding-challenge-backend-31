package wanted.domain.product.dto.response;

import java.util.Map;

public record ProductRatingResponse(
        Double average,
        Integer count,
        Distribution distribution
) {
    public static ProductRatingResponse of(Double avg, Integer count, Map<Integer, Integer> dist) {
        return new ProductRatingResponse(
                avg,
                count,
                new Distribution(
                        dist.getOrDefault(5, 0),
                        dist.getOrDefault(4, 0),
                        dist.getOrDefault(3, 0),
                        dist.getOrDefault(2, 0),
                        dist.getOrDefault(1, 0)
                )
        );
    }

    public record Distribution(
            Integer five,
            Integer four,
            Integer three,
            Integer two,
            Integer one
    ) {}
}