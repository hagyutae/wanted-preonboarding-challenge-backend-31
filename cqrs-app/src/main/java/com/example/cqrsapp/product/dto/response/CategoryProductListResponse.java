package com.example.cqrsapp.product.dto.response;

import com.example.cqrsapp.common.dto.ProductSummaryItem;
import com.example.cqrsapp.product.domain.Category;
import com.example.cqrsapp.common.response.PaginationDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CategoryProductListResponse {

    public CategoryDto category;
    private List<ProductSummaryItem> item;
    private PaginationDto pagination;

    public CategoryProductListResponse(Category category, Page<ProductSummaryItem> page) {
        this.category = CategoryDto.fromEntity(category);
        this.item = page.getContent();
        this.pagination = PaginationDto.builder()
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .perPage(page.getNumberOfElements())
                .build();
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CategoryDto {
        private long id;
        private String name;
        private String slug;
        private boolean isPrimary;
        private CategoryParentResponse parent;

        public static CategoryDto fromEntity(Category category) {
            return CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .slug(category.getSlug())
                    .isPrimary(category.getLevel() == 1)
                    .parent(category.getParent() != null ?
                            CategoryParentResponse.fromCategoryParent(category.getParent()) : null)
                    .build();
        }
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CategoryParentResponse {
        private long id;
        private String name;
        private String slug;

        public static CategoryParentResponse fromCategoryParent(Category parent) {
            return CategoryParentResponse.builder()
                    .id(parent.getId())
                    .name(parent.getName())
                    .slug(parent.getSlug())
                    .build();
        }
    }
}
