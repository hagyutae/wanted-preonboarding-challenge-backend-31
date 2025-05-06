package com.wanted.mono.domain.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateResponse {
    private Long id;
    private String name;
    private String slug;
    private LocalDateTime updatedAt;
}
