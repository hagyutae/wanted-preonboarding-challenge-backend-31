package com.mkhwang.wantedcqrs.product.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name = "categories")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @Column(unique = true)
  private String slug;
  @Column(columnDefinition = "text")
  private String description;
  private String imageUrl;
  @Column(nullable = false)
  private Integer level;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;
}
