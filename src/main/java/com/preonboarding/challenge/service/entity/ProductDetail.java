package com.preonboarding.challenge.service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "product_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    private Double weight;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String dimensions; // JSON: {"width": float, "height": float, "depth": float}

    @Column(length = 500)
    private String materials;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "warranty_info", length = 1000)
    private String warrantyInfo;

    @Column(name = "care_instructions", length = 1000)
    private String careInstructions;

    @Column(name = "additional_info", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String additionalInfo; // JSON object for additional information
}
