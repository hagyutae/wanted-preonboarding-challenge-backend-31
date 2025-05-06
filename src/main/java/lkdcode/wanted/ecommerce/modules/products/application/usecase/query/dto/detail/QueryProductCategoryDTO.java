package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail;

import lombok.Builder;

@Builder
public record QueryProductCategoryDTO(
    Long id,
    String name,
    String slug,
    Boolean is_primary,
    ParentDTO parent
) {

    @Builder
    public record ParentDTO(
        Long id,
        String name,
        String slug
    ) {
    }
}
