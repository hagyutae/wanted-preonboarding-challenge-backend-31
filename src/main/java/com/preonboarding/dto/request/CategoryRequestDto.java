package com.preonboarding.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDto {
    private Long categoryId;
    private boolean isPrimary;
}
