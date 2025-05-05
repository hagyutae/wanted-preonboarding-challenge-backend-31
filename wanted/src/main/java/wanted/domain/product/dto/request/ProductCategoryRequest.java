package wanted.domain.product.dto.request;

import jakarta.validation.constraints.NotNull;

public record ProductCategoryRequest(
        @NotNull(message = "카테고리 ID는 필수입니다.")
        Long categoryId,

        @NotNull(message = "기본 카테고리 여부는 필수입니다.")
        Boolean isPrimary
) {}
