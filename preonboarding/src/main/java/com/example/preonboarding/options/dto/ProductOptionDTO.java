package com.example.preonboarding.options.dto;

import com.example.preonboarding.options.domain.ProductOptionGroup;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductOptionDTO {
    private Long id;
    private String name;
    private boolean stock;
    private int displayOrder;
    private List<OptionDTO> options;

    @QueryProjection
    @Builder
    public ProductOptionDTO(Long productsId, Integer stock) {
        this.id = productsId;
        this.stock = isStock(stock);
    }

    public ProductOptionDTO(ProductOptionGroup i) {
        this.id = i.getId();
        this.name = i.getName();
        this.displayOrder = i.getDisplayOrder();
        this.options = i.getOptionGroups().stream().map(OptionDTO::new).collect(Collectors.toList());
    }

    private boolean isStock(Integer stock) {
        if(stock != null) return stock > 0;
        return false;
    }
}
