package com.wanted.ecommerce.product.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_option_groups")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private String name;
    @Column(name = "display_order")
    private Integer displayOrder;

    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options;

    public static ProductOptionGroup of(Product product, String name, Integer displayOrder) {
        return ProductOptionGroup.builder()
            .product(product)
            .name(name)
            .displayOrder(displayOrder)
            .build();
    }

    public static ProductOptionGroup of(Long id, Product product, String name, Integer displayOrder) {
        return ProductOptionGroup.builder()
            .id(id)
            .product(product)
            .name(name)
            .displayOrder(displayOrder)
            .build();
    }

    public void update(String name, Integer displayOrder, List<ProductOption>options){
        this.name = name;
        this.displayOrder = displayOrder;
        this.options = options;
    }

    public void updateProduct(Product product){
        this.product = product;
    }
}
