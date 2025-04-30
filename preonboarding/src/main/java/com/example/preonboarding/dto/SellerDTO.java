package com.example.preonboarding.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SellerDTO {
    private Long id;
    private String name;
    private String decription;
    private String logo_url;
    private Double rating;
    private String contactEmail;
    private String contactPhone;

    @QueryProjection
    public SellerDTO(Long id, String name, String decription, String logo_url, Double rating, String contactEmail, String contactPhone) {
        this.id = id;
        this.name = name;
        this.decription = decription;
        this.logo_url = logo_url;
        this.rating = rating;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }
}
