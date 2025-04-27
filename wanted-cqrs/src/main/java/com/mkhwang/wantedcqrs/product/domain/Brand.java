package com.mkhwang.wantedcqrs.product.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name = "brands")
public class Brand {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(unique = true)
  private String slug;

  @Column(name = "description", columnDefinition = "text")
  private String description;
  private String logoUrl;
  private String website;
}
