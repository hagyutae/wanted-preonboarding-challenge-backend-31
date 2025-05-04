package investLee.platform.ecommerce.domain.entity;

import investLee.platform.ecommerce.dto.request.ProductImageUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_images")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String altText;

    private boolean isPrimary;

    private int displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ProductOptionEntity option;  // nullable

    public void update(ProductImageUpdateRequest dto, ProductOptionEntity option) {
        this.url = dto.getUrl();
        this.altText = dto.getAltText();
        this.isPrimary = dto.isPrimary();
        this.displayOrder = dto.getDisplayOrder();
        this.option = option;
    }
}
