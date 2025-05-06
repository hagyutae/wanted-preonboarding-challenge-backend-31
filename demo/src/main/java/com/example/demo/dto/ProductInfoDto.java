package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDto {
    private Long id;
    private String name;
    private String slug;
    private String fullDescription;
    private String shortDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer sellerId;
    private Integer brandId;
    private String status;

    private SellerDto seller;
    private BrandDto brand;
    private ProductDetailDto detail;
    private ProductPriceDto price;
    private List<ProductOptionsGroupDto> optionGroups;
    private List<ProductImageDto> images;
    private List<CategoryDto> categories;
    private List<TagDto> tags;
}
