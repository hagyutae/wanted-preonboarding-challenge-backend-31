package investLee.platform.ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_image")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private String url;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = true)
    private ProductOptionEntity option;

    public void update(
            String url,
            String altText,
            Boolean isPrimary,
            Integer displayOrder,
            ProductOptionEntity option)
    {
        this.url = url;
        this.altText = altText;
        this.isPrimary = isPrimary;
        this.displayOrder = displayOrder;
        this.option = option;
    }
}

