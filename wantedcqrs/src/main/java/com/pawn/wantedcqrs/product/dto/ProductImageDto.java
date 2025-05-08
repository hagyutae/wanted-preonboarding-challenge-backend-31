package com.pawn.wantedcqrs.product.dto;

import lombok.Data;

@Data
public class ProductImageDto {

    private Long id;

    private String url;

    private String altText;

    private boolean isPrimary;

    private int displayOrder;

    private Long productId;

    private Long optionId;

}
