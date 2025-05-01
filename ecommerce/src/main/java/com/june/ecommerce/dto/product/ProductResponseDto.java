package com.june.ecommerce.dto.product;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
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
}
