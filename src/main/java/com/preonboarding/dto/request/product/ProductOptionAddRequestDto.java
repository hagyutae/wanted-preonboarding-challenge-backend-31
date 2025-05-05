package com.preonboarding.dto.request.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionAddRequestDto extends ProductOptionRequestDto {
    private Long optionGroupId;
}
