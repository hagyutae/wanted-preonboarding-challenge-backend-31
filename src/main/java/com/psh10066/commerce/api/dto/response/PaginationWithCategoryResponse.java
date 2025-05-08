package com.psh10066.commerce.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.psh10066.commerce.domain.model.category.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PaginationWithCategoryResponse<T>(
    CategoryDto category,
    List<T> items,
    Pagination pagination
) {

    public static <T, R> PaginationWithCategoryResponse<R> of(Category category, Page<T> page, Function<T, R> mapper) {
        return new PaginationWithCategoryResponse<>(
            new CategoryDto(category.getId(), category.getName(), category.getSlug(), category.getDescription(), category.getLevel(), category.getImageUrl(),
                category.getParent() != null ? new Parent(category.getParent().getId(), category.getParent().getName(), category.getParent().getSlug()) : null),
            page.getContent().stream().map(mapper).toList(),
            new Pagination(page.getTotalElements(), page.getTotalPages(), page.getNumber() + 1, page.getSize())
        );
    }

    public record CategoryDto(
        Long id,
        String name,
        String slug,
        String description,
        Integer level,
        String imageUrl,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Parent parent
    ) {
    }

    public record Parent(
        Long id,
        String name,
        String slug
    ) {
    }

    public record Pagination(
        long totalItems,
        int totalPages,
        int currentPage,
        int perPage
    ) {
    }
}