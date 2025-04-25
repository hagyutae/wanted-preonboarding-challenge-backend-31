package com.mkhwang.wantedcqrs.product.domain;

import com.mkhwang.wantedcqrs.config.audit.BaseCreateUpdateAudit;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name = "products")
public class Product extends BaseCreateUpdateAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String slug;
  private String shortDescription;
  private String fullDescription;

  @JoinColumn(name = "seller_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Seller seller;

  @JoinColumn(name = "brand_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Brand brand;

  @Column(nullable = false)
  private String status;
}
