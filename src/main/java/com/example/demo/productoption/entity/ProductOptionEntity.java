package com.example.demo.productoption.entity;

import com.example.demo.product.controller.request.ProductOptionGroupRequest;
import com.example.demo.productoption.controller.request.AddProductOptionRequest;
import com.example.demo.productoption.controller.request.UpdateProductOptionRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "product_options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroupEntity productOptionGroupEntity;
    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;
    @Column(name = "additional_price", columnDefinition = "DECIMAL(13, 2) DEFAULT 0")
    private BigDecimal additionalPrice;
    @Column(name = "sku", columnDefinition = "VARCHAR(100)")
    private String sku;
    @Column(name = "stock", columnDefinition = "INTEGER DEFAULT 0")
    private Integer stock;
    @Column(name = "display_order", columnDefinition = "INTEGER DEFAULT 0")
    private Integer displayOrder;

    public static ProductOptionEntity create(ProductOptionGroupRequest.ProductOptionRequest request, ProductOptionGroupEntity productOptionGroupEntity) {
        return new ProductOptionEntity(
                null,
                productOptionGroupEntity,
                request.name(),
                request.additionalPrice(),
                request.sku(),
                request.stock(),
                request.displayOrder()
        );
    }

    public static ProductOptionEntity create(AddProductOptionRequest request, ProductOptionGroupEntity productOptionGroupEntity) {
        return new ProductOptionEntity(
                null,
                productOptionGroupEntity,
                request.name(),
                request.additionalPrice(),
                request.sku(),
                request.stock(),
                request.displayOrder()
        );
    }

    public void update(UpdateProductOptionRequest request) {
        this.name = request.name();
        this.additionalPrice = request.additionalPrice();
        this.sku = request.sku();
        this.stock = request.stock();
        this.displayOrder = request.displayOrder();
    }
}
