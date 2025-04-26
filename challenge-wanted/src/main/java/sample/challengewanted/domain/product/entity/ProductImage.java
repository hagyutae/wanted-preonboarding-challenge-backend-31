package sample.challengewanted.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_images")
@Entity
public class ProductImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String altText;
    private boolean isPrimary;
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private ProductOption option;

    public void assignProductOption(ProductOption option) {
        this.option = option;
    }

}
