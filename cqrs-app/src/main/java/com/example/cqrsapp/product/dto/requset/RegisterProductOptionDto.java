package com.example.cqrsapp.product.dto.requset;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterProductOptionDto {
    private Long optionGroupId;
    private String name;
    private Integer additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;
}
