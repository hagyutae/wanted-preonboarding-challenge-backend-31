package com.ecommerce.product.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "product_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Double weight;

    @Type(JsonType.class)
    @Column(columnDefinition = "JSONB")
    private DimensionsVO dimensions;

    private String materials;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "warranty_info")
    private String warrantyInfo;

    @Column(name = "care_instructions")
    private String careInstructions;

    @Type(JsonType.class)
    @Column(columnDefinition = "JSONB")
    private AdditionalInfoVO additionalInfo;

    @Builder
    public ProductDetail(
            Product product,
            Double weight,
            DimensionsVO dimensions,
            String materials,
            String countryOfOrigin,
            String warrantyInfo,
            String careInstructions,
            AdditionalInfoVO additionalInfo) {
        this.product = product;
        this.weight = weight;
        this.dimensions = dimensions;
        this.materials = materials;
        this.countryOfOrigin = countryOfOrigin;
        this.warrantyInfo = warrantyInfo;
        this.careInstructions = careInstructions;
        this.additionalInfo = additionalInfo;
    }
}
