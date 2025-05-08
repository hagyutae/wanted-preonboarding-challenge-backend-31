package com.pawn.wantedcqrs.product.dto;

import lombok.Data;

@Data
public class ProductTagDto {

    private Long id;

    private Long productId;

    private Long tagId;

}
