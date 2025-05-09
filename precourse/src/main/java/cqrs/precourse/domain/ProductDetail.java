package cqrs.precourse.domain;

import cqrs.precourse.converter.JsonbConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "product_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // joincolum cascade jpa에서만 작동
    // ondelete cascade db에서도 작동
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonbConverter.class)
    private Map<String, Object> dimensions;

    @Column(columnDefinition = "TEXT")
    private String materials;

    @Column(length = 100)
    private String countryOfOrigin;

    @Column(columnDefinition = "TEXT")
    private String warrantyInfo;

    @Column(columnDefinition = "TEXT")
    private String careInstructions;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = JsonbConverter.class)
    private Map<String, Object> additionalInfo;


}
