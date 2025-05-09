package com.wanted.mono.domain.product.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SellerDto {
    private Long id;

    private String name;

    private String description;

    @JsonProperty("logo_url")
    private String logoUrl;

    private BigDecimal rating;

    @JsonProperty("contact_email")
    private String contactEmail;

    @JsonProperty("contact_phone")
    private String contactPhone;



}
