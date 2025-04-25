package com.mkhwang.wantedcqrs.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Entity(name = "product_details")
public class ProductDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;
  @Column(precision = 10, scale = 2)
  private BigDecimal weight;
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> dimensions;
  @Lob
  private String materials;
  private String countryOfOrigin;
  @Lob
  private String warrantyInfo;
  @Lob
  private String careInstructions;
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> additionalInfo;
}
