package com.mkhwang.wantedcqrs.product.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
public class SellerDto {
  private final Long id;
  private final String name;
//  private final String description;
  private final String logoUrl;
  private final BigDecimal rating;
  private final String contactEmail;
  private final String contactPhone;
  private Instant createdAt;

  public SellerDto(Long id, String name, String logoUrl, BigDecimal rating, String contactEmail, String contactPhone) {
    this.id = id;
    this.name = name;
    this.logoUrl = logoUrl;
    this.rating = rating;
    this.contactEmail = contactEmail;
    this.contactPhone = contactPhone;
  }

  @QueryProjection
  public SellerDto(Long id, String name, String logoUrl, BigDecimal rating, String contactEmail, String contactPhone, Instant createdAt) {
    this.id = id;
    this.name = name;
    this.logoUrl = logoUrl;
    this.rating = rating;
    this.contactEmail = contactEmail;
    this.contactPhone = contactPhone;
    this.createdAt = createdAt;
  }
}
