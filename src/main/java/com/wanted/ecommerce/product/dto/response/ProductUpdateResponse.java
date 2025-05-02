package com.wanted.ecommerce.product.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ProductUpdateResponse(
    Long id,
    String name,
    String slug,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    LocalDateTime updatedAt
) {

    public static ProductUpdateResponse of(long id, String name, String slug,
        LocalDateTime updatedAt) {
        return ProductUpdateResponse.builder()
            .id(id)
            .name(name)
            .slug(slug)
            .updatedAt(updatedAt)
            .build();
    }
}
