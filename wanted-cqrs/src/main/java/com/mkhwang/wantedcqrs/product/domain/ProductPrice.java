package com.mkhwang.wantedcqrs.product.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity(name = "product_prices")
public class ProductPrice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;
  @Column(precision = 12, scale = 2)
  private BigDecimal basePrice;
  @Column(precision = 12, scale = 2)
  private BigDecimal salePrice;
  @Column(precision = 12, scale = 2)
  private BigDecimal costPrice;
  private String currency = "KRW";
  @Column(precision = 5, scale = 2)
  private BigDecimal taxRate;
}
