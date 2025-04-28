package com.example.preonboarding.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductOptionDTO {
    private Long productsId;
    private boolean stock;

    @QueryProjection
    @Builder
    public ProductOptionDTO(Long productsId, Integer stock) {
        this.productsId = productsId;
        this.stock = isStock(stock);
    }

    private boolean isStock(Integer stock) {
        if(stock != null) return stock > 0;
        return false;
    }
}
