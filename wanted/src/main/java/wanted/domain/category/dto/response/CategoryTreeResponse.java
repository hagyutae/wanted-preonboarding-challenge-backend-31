package wanted.domain.category.dto.response;

import wanted.domain.category.entity.Category;

import java.util.List;

public record CategoryTreeResponse(
        Long id,
        String name,
        String slug,
        String description,
        int level,
        String imageUrl,
        List<CategoryTreeResponse> children
) {
    public static CategoryTreeResponse of(Category category) {
        return new CategoryTreeResponse(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getLevel(),
                category.getImageUrl(),
                category.getChildren().stream()
                        .map(CategoryTreeResponse::of)
                        .toList()
        );
    }
}