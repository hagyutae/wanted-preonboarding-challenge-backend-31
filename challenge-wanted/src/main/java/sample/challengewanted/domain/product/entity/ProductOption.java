package sample.challengewanted.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    private ProductOption(Long id, String name, BigDecimal additionalPrice, String sku, Integer stock, Integer displayOrder, ProductOptionGroup productOptionGroup) {
        this.id = id;
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.sku = sku;
        this.stock = stock;
        this.displayOrder = displayOrder;
        this.productOptionGroup = productOptionGroup;
    }

    public static ProductOption of(String name, String sku, Integer stock, Integer displayOrder, ProductOptionGroup productOptionGroup) {
        return ProductOption.builder()
                .name(name)
                .sku(sku)
                .stock(stock)
                .displayOrder(displayOrder)
                .productOptionGroup(productOptionGroup)
                .build();
    }

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
