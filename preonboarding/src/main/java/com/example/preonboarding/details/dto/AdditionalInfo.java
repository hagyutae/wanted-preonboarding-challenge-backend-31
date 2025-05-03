package com.example.preonboarding.details.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class AdditionalInfo {

    private Boolean assembly_required;
    private String assembly_time;


    public AdditionalInfo(Boolean assemblyRequired, String assemblyTime) {
        this.assembly_required = assemblyRequired;
        this.assembly_time = assemblyTime;
    }
}
