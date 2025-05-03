package com.preonboarding.domain;

import com.preonboarding.dto.request.ProductImageRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "product_images")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ProductOption option;

    @Column(nullable = false)
    private String url;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name = "display_order")
    private Integer displayOrder;

    public static ProductImage of(ProductImageRequestDto dto) {
        return ProductImage.builder()
                .url(dto.getUrl())
                .altText(dto.getAltText())
                .isPrimary(dto.isPrimary())
                .displayOrder(dto.getDisplayOrder())
                .build();
    }

    public void updateProductOption(ProductOption productOption) {
        this.option = productOption;
    }

    public void updateProduct(Product product) {
        if (this.product != null) {
            this.product.getProductImageList().remove(this);
        }

        this.product = product;
        product.getProductImageList().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        if (id == null && that.id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
