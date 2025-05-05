package com.example.cqrsapp.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "product_option_groups")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ProductOptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 100)
    private String name;

    private Integer displayOrder;

    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> productOptions = new ArrayList<>();

    @Builder
    public ProductOptionGroup(Long id, Product product, String name, Integer displayOrder, List<ProductOption> productOptions) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.displayOrder = displayOrder;

        for (ProductOption productOption : productOptions) {
            productOption.setOptionGroup(this);
            this.productOptions.add(productOption);
        }
    }

    public void clearOption() {
        productOptions.clear();
    }
}