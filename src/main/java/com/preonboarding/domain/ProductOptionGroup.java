package com.preonboarding.domain;

import com.preonboarding.dto.request.product.ProductOptionGroupRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product_option_groups")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productOptionGroup",cascade = CascadeType.ALL)
    @Builder.Default
    private List<ProductOption> productOptionList = new ArrayList<>();

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "display_order")
    private Integer displayOrder;

    public static ProductOptionGroup from(Product product,ProductOptionGroupRequestDto dto) {
        return ProductOptionGroup.builder()
                .product(product)
                .name(dto.getName())
                .displayOrder(dto.getDisplayOrder())
                .build();
    }

    public void updateProduct(Product product) {
        if (this.product != null) {
            this.product.getProductOptionGroupList().remove(this);
        }

        this.product = product;
        product.getProductOptionGroupList().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOptionGroup that = (ProductOptionGroup) o;
        if (id == null && that.id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
