package com.preonboarding.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionAddRequestDto extends ProductOptionRequestDto {
    private Long optionGroupId;
}
