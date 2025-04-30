package com.mkhwang.wantedcqrs.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "product_images")
public class ProductImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  private String url;
  private String altText;
  @Column(name = "is_primary")
  private Boolean primary;
  private int displayOrder = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "option_id")
  private ProductOption option;
}
