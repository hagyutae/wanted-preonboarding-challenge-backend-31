package com.example.wanted_preonboarding_challenge_backend_31.domain.model.product;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ProductAdditionalInfo implements Serializable {

    private boolean assemblyRequired;
    private String assemblyTime;


    public static ProductAdditionalInfo create(boolean assemblyRequired, String assemblyTime) {
        return ProductAdditionalInfo.builder()
                .assemblyRequired(assemblyRequired)
                .assemblyTime(assemblyTime)
                .build();
    }
}
