package wanted.domain.product.dto.response;

import wanted.domain.product.entity.ProductOptionGroup;

import java.util.List;

public record ProductOptionGroupResponse(
        Long id,
        String name,
        Integer displayOrder,
        List<ProductOptionResponse> options
) {
    public static ProductOptionGroupResponse of(ProductOptionGroup group) {
        return new ProductOptionGroupResponse(
                group.getId(),
                group.getName(),
                group.getDisplayOrder(),
                group.getOptions().stream()
                        .map(option -> ProductOptionResponse.of(option, false))
                        .toList()
        );
    }
}