package com.mkhwang.wantedcqrs.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@Entity(name = "product_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;
  @Column(precision = 10, scale = 2)
  private BigDecimal weight;
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> dimensions;
  @Column(columnDefinition = "text")
  private String materials;
  private String countryOfOrigin;
  @Column(columnDefinition = "text")
  private String warrantyInfo;
  @Column(name = "care_instructions", columnDefinition = "text")
  private String careInstructions;
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> additionalInfo;
}
