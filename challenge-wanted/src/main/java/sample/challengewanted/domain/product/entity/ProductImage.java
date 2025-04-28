package sample.challengewanted.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_images")
@Entity
public class ProductImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String url;

    @Column(name = "alt_text", length = 255)
    private String altText;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary = false;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private ProductOption option;

    @Builder
    private ProductImage(String url, String altText, boolean isPrimary, Integer displayOrder, Product product, ProductOption option) {
        this.url = url;
        this.altText = altText;
        this.isPrimary = isPrimary;
        this.displayOrder = displayOrder;
        this.product = product;
        this.option = option;
    }

    public static ProductImage create(String url, String altText, boolean isPrimary, Integer displayOrder, Product product, ProductOption option) {
        return ProductImage.builder()
                .url(url)
                .altText(altText)
                .isPrimary(isPrimary)
                .displayOrder(displayOrder)
                .product(product)
                .option(option)
                .build();
    }

    public void assignProductOption(ProductOption option) {
        this.option = option;
    }

}
