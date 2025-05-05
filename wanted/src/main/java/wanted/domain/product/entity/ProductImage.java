package wanted.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.domain.product.dto.ProductImageRequest;

@Entity(name = "product_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private String url;

    private String altText;

    private boolean isPrimary;

    private int displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ProductOption option;

    @Builder
    public ProductImage(Product product, String url, String altText, boolean isPrimary,
                        int displayOrder, ProductOption option) {
        this.product = product;
        this.url = url;
        this.altText = altText;
        this.isPrimary = isPrimary;
        this.displayOrder = displayOrder;
        this.option = option;
    }

    public static ProductImage from(ProductImageRequest dto, Product product, ProductOption option) {
        return ProductImage.builder()
                .product(product)
                .url(dto.url())
                .altText(dto.altText())
                .isPrimary(Boolean.TRUE.equals(dto.isPrimary()))
                .displayOrder(dto.displayOrder() != null ? dto.displayOrder() : 0)
                .option(option)
                .build();
    }
}
