package com.example.demo.productoption.entity;

import com.example.demo.product.controller.request.ProductOptionGroupRequest;
import com.example.demo.product.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "product_option_groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOptionGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "productOptionGroupEntity", cascade = CascadeType.ALL)
    private List<ProductOptionEntity> productOptionEntityList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
    @Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;
    @Column(name = "display_order", columnDefinition = "INTEGER DEFAULT 0")
    private Integer displayOrder;


    public static ProductOptionGroupEntity create(ProductOptionGroupRequest request, ProductEntity productEntity) {
        ProductOptionGroupEntity productOptionGroupEntity = new ProductOptionGroupEntity(
                null,
                null,
                productEntity,
                request.name(),
                request.displayOrder()
        );

        List<ProductOptionEntity> productOptionEntityList = request.options().stream()
                .map(it -> ProductOptionEntity.create(it, productOptionGroupEntity))
                .toList();

        productOptionGroupEntity.connectProductOptionEntityList(productOptionEntityList);

        return productOptionGroupEntity;
    }

    private void connectProductOptionEntityList(List<ProductOptionEntity> productOptionEntityList) {
        this.productOptionEntityList = productOptionEntityList;
    }
}
