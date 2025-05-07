package wanted.domain.review;

public record ReviewSearchCondition(
        Integer page,
        Integer perPage,
        String sort,
        Integer rating
) {}

