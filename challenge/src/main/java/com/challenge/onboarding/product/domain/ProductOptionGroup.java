package com.challenge.onboarding.product.domain;

import com.challenge.onboarding.product.service.dto.request.CreateProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "product_option_groups")
@Builder
@AllArgsConstructor
public class ProductOptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "display_order")
    private Integer displayOrder;

    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options;

    protected ProductOptionGroup() {}

    public static ProductOptionGroup from(CreateProductRequest.OptionGroup optionGroup, Product product) {
        return builder()
                .product(product)
                .name(optionGroup.name())
                .displayOrder(optionGroup.displayOrder())
                .build();
    }
}