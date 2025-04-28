package com.example.preonboarding.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BrandResponse {
    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private String website;

    public BrandResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BrandResponse(Long id, String name, String description, String logoUrl, String website) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.website = website;
    }
}
