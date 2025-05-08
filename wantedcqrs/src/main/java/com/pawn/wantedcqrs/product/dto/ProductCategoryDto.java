package com.pawn.wantedcqrs.product.dto;

import lombok.Data;

@Data
public class ProductCategoryDto {

    private Long id;

    private Long productId;

    private Long categoryId;

    private boolean isPrimary;

}
