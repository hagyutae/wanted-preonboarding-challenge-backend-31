package com.wanted.ecommerce.product.domain;

import com.wanted.ecommerce.product.dto.request.AdditionalInfoRequest;
import com.wanted.ecommerce.product.dto.request.DimensionsRequest;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "product_details")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private DimensionsRequest dimensionsRequest;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private AdditionalInfoRequest additionalInfoRequest;

    public static ProductDetail of(Product product, BigDecimal weight,
        DimensionsRequest dimensionsRequest, String materials, String countryOfOrigin,
        String warrantyInfo, String careInstructions, AdditionalInfoRequest additionalInfoRequest){
        return ProductDetail.builder()
            .product(product)
            .weight(weight)
            .dimensionsRequest(dimensionsRequest)
            .materials(materials)
            .countryOfOrigin(countryOfOrigin)
            .warrantyInfo(warrantyInfo)
            .careInstructions(careInstructions)
            .additionalInfoRequest(additionalInfoRequest)
            .build();
    }
}
