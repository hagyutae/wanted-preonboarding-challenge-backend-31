package minseok.cqrschallenge.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_options")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    private ProductOptionGroup optionGroup;

    @Column(nullable = false)
    private String name;

    @Column(name = "additional_price")
    private BigDecimal additionalPrice = BigDecimal.ZERO;

    @Column(nullable = false)
    private String sku;

    private Integer stock = 0;

    @Column(name = "display_order")
    private Integer displayOrder = 0;
}