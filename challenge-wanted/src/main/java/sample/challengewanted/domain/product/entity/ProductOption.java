package sample.challengewanted.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_options")
@Entity
public class ProductOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;

    @OneToMany(mappedBy = "option")
    private List<ProductImage> productImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup productOptionGroup;

    public void assignProductOption(ProductOptionGroup productOptionGroup) {
        this.productOptionGroup = productOptionGroup;
    }

    public void addProductImage(ProductImage productImage) {
        this.productImages.add(productImage);
        productImage.assignProductOption(this);
    }

    public void addProductOptionGroup(ProductOptionGroup productOptionGroup) {
        this.productOptionGroup = productOptionGroup;
        productOptionGroup.assignProductOption(this);
    }
}
