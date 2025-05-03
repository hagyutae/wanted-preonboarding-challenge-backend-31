package com.preonboarding.domain;

import com.preonboarding.dto.request.ProductDetailRequestDto;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "product_details")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "product_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String,Object> dimensions;

    @Column(columnDefinition = "TEXT")
    private String materials;

    @Column(name = "country_of_origin", length = 100)
    private String countryOfOrigin;

    @Column(name = "warranty_info", columnDefinition = "TEXT")
    private String warrantyInfo;

    @Column(name = "care_instructions", columnDefinition = "TEXT")
    private String careInstructions;

    @Type(JsonType.class)
    @Column(name = "additional_info", columnDefinition = "jsonb")
    private Map<String,Object> additionalInfo;

    public static ProductDetail of(ProductDetailRequestDto dto) {
        return ProductDetail.builder()
                .weight(dto.getWeight())
                .dimensions(dto.getDimensions())
                .materials(dto.getMaterials())
                .countryOfOrigin(dto.getCountryOfOrigin())
                .warrantyInfo(dto.getWarrantyInfo())
                .careInstructions(dto.getCareInstructions())
                .additionalInfo(dto.getAdditionalInfo())
                .build();
    }

    public void updateProduct(Product product) {
        this.product = product;
        product.updateProductDetail(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetail that = (ProductDetail) o;
        if (id == null && that.id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
