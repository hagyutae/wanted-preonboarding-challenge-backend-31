package com.example.preonboarding.response;

import com.example.preonboarding.seller.domain.Sellers;
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
public class SellerResponse {
    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private Double rating;
    private String contactEmail;
    private String contactPhone;

    public SellerResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SellerResponse(Sellers sellers){
        this.id = sellers.getId();
        this.name = sellers.getName();
        this.description = sellers.getDescription();
        this.logoUrl = sellers.getLogoUrl();
        this.rating = sellers.getRating();
        this.contactEmail = sellers.getContactEmail();
        this.contactPhone = sellers.getContactPhone();
    }

    public SellerResponse(Long id, String name, String description, String logoUrl, Double rating, String contactEmail, String contactPhone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.rating = rating;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }
}
