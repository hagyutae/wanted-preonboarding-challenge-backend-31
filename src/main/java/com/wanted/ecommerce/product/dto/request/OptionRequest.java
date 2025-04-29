package com.wanted.ecommerce.product.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OptionRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @Digits(integer = 10, fraction = 2)
    @Column(name = "additional_price")
    private Double additionalPrice;
    @Size(max = 100)
    private String sku;
    private Integer stock;

    @Column(name = "display_order")
    private Integer displayOrder;
}
