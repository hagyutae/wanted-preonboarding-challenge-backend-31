package kr.co.wanted.backend31.common.model.product;

import java.math.BigDecimal;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_details")
public class ProductDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight")
    private BigDecimal weight;
    @Column(name = "dimensions")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> dimensions;
    @Column(name = "materials")
    private String materials;
    @Column(name = "country_of_origin")
    private String countryOfOrigin;
    @Column(name = "warranty_info")
    private String warrantyInfo;
    @Column(name = "care_instructions")
    private String careInstructions;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "additional_info")
    private Map<String, Object> additionalInfo;

    @Setter(AccessLevel.PACKAGE)
    @JoinColumn(name = "product_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Product product;
}
