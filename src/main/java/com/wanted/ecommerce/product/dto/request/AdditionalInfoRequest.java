package com.wanted.ecommerce.product.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionalInfoRequest {

    @Column(name = "assembly_required")
    private boolean assemblyRequired;
    @Column(name = "assembly_time")
    private String assemblyTime;

    public static AdditionalInfoRequest of(boolean assemblyRequired, String assemblyTime){
        return AdditionalInfoRequest.builder()
            .assemblyRequired(assemblyRequired)
            .assemblyTime(assemblyTime)
            .build();
    }
}
