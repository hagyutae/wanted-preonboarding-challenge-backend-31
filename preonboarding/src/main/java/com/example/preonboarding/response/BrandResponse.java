package com.example.preonboarding.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BrandResponse {
    private Long id;
    private String name;

    @Builder
    public BrandResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
