package com.mkhwang.wantedcqrs.product.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name = "tags")
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String slug;
}
