package wanted.domain.category.dto.request;

public record CategoryProductSearchCondition(
        Integer page,
        Integer perPage,
        String sort,
        Boolean includeSubcategories
) {
}
