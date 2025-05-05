package com.preonboarding.dto.request.product;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionGroupRequestDto {
    private String name;
    private int displayOrder;
    private List<ProductOptionRequestDto> options;
}
