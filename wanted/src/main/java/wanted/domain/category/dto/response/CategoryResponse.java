package wanted.domain.category.dto.response;

import wanted.domain.category.entity.Category;

public record CategoryResponse(
        Long id,
        String name,
        String slug,
        Boolean isPrimary,
        ParentCategoryResponse parent
) {
    public static CategoryResponse of(Category category, Boolean isPrimary) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getSlug(),
                isPrimary,
                category.getParent() != null
                        ? new ParentCategoryResponse(
                        category.getParent().getId(),
                        category.getParent().getName(),
                        category.getParent().getSlug()
                )
                        : null
        );
    }
    public record ParentCategoryResponse(
            Long id,
            String name,
            String slug
    ) {}
}