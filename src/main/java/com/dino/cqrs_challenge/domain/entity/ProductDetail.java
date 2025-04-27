package com.dino.cqrs_challenge.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product_details")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(columnDefinition = "jsonb")
    private String dimensions;

    @Column(columnDefinition = "text")
    private String materials;

    @Column(length = 100)
    private String countryOfOrigin;

    @Column(columnDefinition = "text")
    private String warrantyInfo;

    @Column(columnDefinition = "text")
    private String careInstructions;

    @Column(columnDefinition = "jsonb")
    private String additionalInfo;

}
