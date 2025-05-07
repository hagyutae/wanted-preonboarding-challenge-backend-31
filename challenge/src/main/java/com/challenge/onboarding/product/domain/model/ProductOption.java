package com.challenge.onboarding.product.domain.model;

import com.challenge.onboarding.product.service.dto.request.CreateProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product_options")
@Builder
@AllArgsConstructor
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    private ProductOptionGroup optionGroup;

    private String name;

    @Column(name = "additional_price", precision = 12, scale = 2)
    private BigDecimal additionalPrice;

    private String sku;

    private Integer stock;

    @Column(name = "display_order")
    private Integer displayOrder;

    protected ProductOption() {}

    public static List<ProductOption> initListOption(CreateProductRequest.OptionGroup option) {
        return option.options().stream()
                .map(o -> ProductOption.builder()
                        .name(o.name())
                        .additionalPrice(BigDecimal.valueOf(o.additionalPrice()))
                        .sku(o.sku())
                        .stock(o.stock())
                        .displayOrder(o.displayOrder())
                        .build())
                .toList();
    }
}
