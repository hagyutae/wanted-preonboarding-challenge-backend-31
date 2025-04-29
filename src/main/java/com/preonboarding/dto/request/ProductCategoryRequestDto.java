package com.preonboarding.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryRequestDto {
    private Long categoryId;
    private boolean isPrimary;
}
