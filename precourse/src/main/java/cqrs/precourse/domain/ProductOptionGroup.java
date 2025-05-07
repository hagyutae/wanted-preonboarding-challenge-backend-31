package cqrs.precourse.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "product_option_groups")
public class ProductOptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 100, nullable = false)
    private String name;

    private Integer displayOrder = 0;
}
