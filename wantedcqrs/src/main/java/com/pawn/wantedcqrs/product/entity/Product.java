package com.pawn.wantedcqrs.product.entity;

import com.pawn.wantedcqrs.common.domain.AbstractDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
@Builder
public class Product extends AbstractDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    name: 상품명
    @Column(name = "name", nullable = false)
    private String name;

    //    slug: URL 슬러그 (SEO 최적화용)
    @Column(name = "slug", length = 100, nullable = false, unique = true)
    private String slug;

    //    short_description: 짧은 설명
    @Column(name = "short_description", length = 500)
    private String shortDescription;

    //    full_description: 전체 설명 (HTML 허용)
    @Column(name = "full_description")
    private String fullDescription;

    //    seller_id: 판매자 ID (FK)
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    //    brand_id: 브랜드 ID (FK)
    @Column(name = "brand_id", nullable = false)
    private Long brandId;

    //    status: 상태 (판매중, 품절, 삭제됨 등)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, optional = false)
    private ProductDetail productDetail;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, optional = false)
    private ProductPrice productPrice;

    protected Product(Long id, String name, String slug, String shortDescription, String fullDescription, Long sellerId, Long brandId, ProductStatus status, ProductDetail productDetail, ProductPrice productPrice) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.sellerId = sellerId;
        this.brandId = brandId;
        this.status = status;
        this.setProductDetail(productDetail);
        this.setProductPrice(productPrice);
    }

    public void setProductDetail(ProductDetail productDetail) {
        if (Objects.nonNull(productDetail)) {
            productDetail.setProduct(this);
        }
        this.productDetail = productDetail;
    }

    public void setProductPrice(ProductPrice productPrice) {
        if (Objects.nonNull(productPrice)) {
            productPrice.setProduct(this);
        }
        this.productPrice = productPrice;
    }

}
