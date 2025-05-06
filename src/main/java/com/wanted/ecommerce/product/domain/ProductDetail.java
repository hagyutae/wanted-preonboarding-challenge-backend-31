package com.wanted.ecommerce.product.domain;

import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest.ProductDetailRequest;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Map;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Dimensions dimensions;
    private String materials;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "warranty_info")
    private String warrantyInfo;

    @Column(name = "care_instructions")
    private String careInstructions;

    @Type(JsonType.class)
    @Column(name = "additional_info", columnDefinition = "jsonb")
    private Map<String, Object> additionalInfo;

    public static ProductDetail of(Product product, BigDecimal weight,
        Dimensions dimensions, String materials, String countryOfOrigin,
        String warrantyInfo, String careInstructions, Map<String, Object> additionalInfo){
        return ProductDetail.builder()
            .product(product)
            .weight(weight)
            .dimensions(dimensions)
            .materials(materials)
            .countryOfOrigin(countryOfOrigin)
            .warrantyInfo(warrantyInfo)
            .careInstructions(careInstructions)
            .additionalInfo(additionalInfo)
            .build();
    }

    public void update(ProductDetailRequest request, Dimensions dimensions){
        this.weight = request.getWeight();
        this.dimensions = dimensions;
        this.materials = request.getMaterials();
        this.countryOfOrigin = request.getCountryOfOrigin();
        this.warrantyInfo = request.getWarrantyInfo();
        this.careInstructions = request.getCareInstructions();
        this.additionalInfo = request.getAdditionalInfo();
    }
}
