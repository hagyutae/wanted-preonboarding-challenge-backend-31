package com.example.preonboarding.details.domain;

import com.example.preonboarding.products.domain.Products;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "product_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    private double weight;

    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode dimensions;

    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode additionalInfo;


    public void setProducts(Products products) {
        this.products = products;
    }
}
