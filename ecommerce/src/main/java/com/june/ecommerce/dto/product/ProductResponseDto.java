package com.june.ecommerce.dto.product;

import com.june.ecommerce.domain.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProductResponseDto {
    private int id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String brandName;
    private String sellerName;
    private List<String> categoryNames;
    private List<String> tagNames;

    public static ProductResponseDto fromEntity(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setStatus(product.getStatus());
        dto.setShortDescription(product.getShortDescription());
        return dto;
    }
}
