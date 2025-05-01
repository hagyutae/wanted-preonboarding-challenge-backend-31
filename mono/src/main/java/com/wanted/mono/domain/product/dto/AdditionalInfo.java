package com.wanted.mono.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalInfo {
    @JsonProperty("assembly_required")
    private boolean assemblyRequired;
    @JsonProperty("assembly_time")
    private String assemblyTime;
}
