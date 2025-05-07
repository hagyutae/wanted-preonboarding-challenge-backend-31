package wanted.domain.product.dto.response;

import wanted.domain.product.entity.Product;

import java.time.LocalDateTime;

public record ProductCreateResponse(
        Long id,
        String name,
        String slug,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProductCreateResponse of(Product product) {
        return new ProductCreateResponse(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
