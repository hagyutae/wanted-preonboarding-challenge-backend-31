package com.wanted.mono.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalInfo {
    private String assemblyRequired;
    private String assemblyTime;
}
