package com.mkhwang.wantedcqrs.product.domain;

import com.mkhwang.wantedcqrs.config.audit.BaseCreateAudit;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity(name = "sellers")
public class Seller extends BaseCreateAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(name = "description", columnDefinition = "text")
  private String description;
  private String logoUrl;
  @Column(precision = 3, scale = 2)
  private BigDecimal rating;
  private String contactEmail;
  private String contactPhone;
}
