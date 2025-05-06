package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.framework.common.jpa.entity.EntityValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.ProductDetailModel;
import lkdcode.wanted.ecommerce.modules.products.domain.value.detail.ProductCountryOfOrigin;
import lkdcode.wanted.ecommerce.modules.products.domain.value.detail.ProductWeight;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Entity
@Table(name = "product_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailJpaEntity extends EntityValidator<ProductDetailJpaEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductJpaEntity product;

    @Digits(integer = ProductWeight.INTEGER, fraction = ProductWeight.FRACTION)
    @Column(precision = ProductWeight.PRECISION, scale = ProductWeight.SCALE)
    private BigDecimal weight;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> dimensions;

    @Column(columnDefinition = "text")
    private String materials;

    @Size(max = ProductCountryOfOrigin.LENGTH)
    @Column(name = "country_of_origin", length = ProductCountryOfOrigin.LENGTH)
    private String countryOfOrigin;

    @Column(name = "warranty_info", columnDefinition = "text")
    private String warrantyInfo;

    @Column(name = "care_instructions", columnDefinition = "text")
    private String careInstructions;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "additional_info", columnDefinition = "jsonb")
    private Map<String, Object> additionalInfo;

    @Builder
    public ProductDetailJpaEntity(Long id, ProductJpaEntity product, BigDecimal weight, Map<String, Object> dimensions, String materials, String countryOfOrigin, String warrantyInfo, String careInstructions, Map<String, Object> additionalInfo) {
        this.id = id;
        this.product = product;
        this.weight = weight;
        this.dimensions = dimensions;
        this.materials = materials;
        this.countryOfOrigin = countryOfOrigin;
        this.warrantyInfo = warrantyInfo;
        this.careInstructions = careInstructions;
        this.additionalInfo = additionalInfo;
        super.validSelf();
    }

    public void update(final ProductDetailModel model) {
        this.weight = model.weight().value();
        this.dimensions = model.additionalInfo().value();
        this.materials = model.material().value();
        this.countryOfOrigin = model.countryOfOrigin().value();
        this.warrantyInfo = model.warrantyInfo().value();
        this.careInstructions = model.careInstructions().value();
        this.additionalInfo = model.additionalInfo().value();
        super.validSelf();
    }
}