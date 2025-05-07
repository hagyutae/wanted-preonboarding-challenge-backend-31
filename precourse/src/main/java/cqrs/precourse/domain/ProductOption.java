package cqrs.precourse.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "product_options")
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup productOptionGroup;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(precision = 12, scale = 2)
    private BigDecimal additionalPrice = BigDecimal.ZERO;

    @Column(length = 100)
    private String sku;

    private Integer stokck = 0;

    private Integer displayOrder = 0;
}

