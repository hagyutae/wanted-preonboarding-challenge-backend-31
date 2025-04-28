package com.mkhwang.wantedcqrs.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "product_options")
public class ProductOption {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "option_group_id")
  private ProductOptionGroup group;

  @Column(nullable = false)
  private String name;

  @Column(precision = 12, scale = 2)
  private BigDecimal additionalPrice;

  private String sku;
  private int stock = 0;
  private int displayOrder = 0;
}
